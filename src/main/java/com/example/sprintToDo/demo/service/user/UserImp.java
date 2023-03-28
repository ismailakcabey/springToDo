package com.example.sprintToDo.demo.service.user;

import com.example.sprintToDo.demo.dto.CreateUserDto;
import com.example.sprintToDo.demo.dto.UserLogin;
import com.example.sprintToDo.demo.service.email.EmailService;
import com.example.sprintToDo.demo.service.fileUpload.ExcelFileUpload;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import com.example.sprintToDo.demo.dto.UserDto;
import com.example.sprintToDo.demo.entity.User;
import com.example.sprintToDo.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserImp implements UserService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    private final EmailService emailService;

    private final ExcelFileUpload excelFileUpload;

    @Override
    public UserDto createUser(CreateUserDto createUserDto) throws MailjetSocketTimeoutException, MailjetException {
        User user = modelMapper.map(createUserDto,User.class);
        user.setCreatedAt(new Date());
        user.setCreatedById(1L);
        emailService.sendEmail(user.getEmail());
        String hashed = BCrypt.hashpw(user.getPassword(),BCrypt.gensalt());
        user.setPassword(hashed);
        return modelMapper.map(userRepository.save(user),UserDto.class);
    }

    @Override
    public List<UserDto> listUser() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream().map(user->modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public Boolean userLogin(UserLogin user) {
        User loginUser = userRepository.findByEmail(user.getEmail());
        if(BCrypt.checkpw(user.getPassword(),loginUser.getPassword())){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public Boolean excelExportUser(UserLogin sendMailUser) {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream().map(user->modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
        String[] row_heading = {"fullName","email","birthdDate","phoneNumber"};
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet( "User Details ");
        Row headerRow = spreadsheet.createRow(0);
        // Creating header
        for (int i = 0; i < row_heading.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(row_heading[i]);
        }
        // Creating data rows for each user
        for(int i = 0; i < users.size(); i++) {
            Row dataRow = spreadsheet.createRow(i + 1);
            dataRow.createCell(0).setCellValue(users.get(i).getFullName());
            dataRow.createCell(1).setCellValue(users.get(i).getEmail());
            dataRow.createCell(2).setCellValue(users.get(i).getBirthDateAt());
            dataRow.createCell(3).setCellValue(users.get(i).getPhoneNumber());
        }
        //Write the workbook in file system
        FileOutputStream out;
        try {
            var date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = formatter.format(date);
            String replaced = "springBootToDo";
            final String fileName =  "/Users/ismailakca/desktop/cvDeneme/user"+replaced+".xlsx";
            out = new FileOutputStream( new File("/Users/ismailakca/desktop/cvDeneme/user"+replaced+".xlsx"));
            String filePath = "/Users/ismailakca/desktop/cvDeneme/user"+replaced+".xlsx";
            workbook.write(out);
            out.close();
            excelFileUpload.s3Upload(sendMailUser,filePath,replaced,"xlsx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public UserDto updateUser(UserDto user, Long id) {
        Optional<User> updateUser = userRepository.findById(id);
        if(!(updateUser.isPresent())) return null;
        if(user.getEmail() != null) updateUser.get().setEmail(user.getEmail());
        if(user.getFullName() != null) updateUser.get().setFullName(user.getFullName());
        if(user.getPhoneNumber() != null) updateUser.get().setPhoneNumber(user.getPhoneNumber());
        if(user.getBirthDateAt() != null) updateUser.get().setBirthDateAt(user.getBirthDateAt());
        if(user.getEmailStatus() != null) updateUser.get().setEmailStatus(user.getEmailStatus());
        updateUser.get().setUpdatedAt(new Date());
        updateUser.get().setUpdatedById(1L);
        return modelMapper.map(userRepository.save(updateUser.get()),UserDto.class);
    }

    @Override
    public Boolean deleteUser(Long id) {
        Optional<User> deleteUser = userRepository.findById(id);
        if(!(deleteUser.isPresent())) return false;
        userRepository.deleteById(id);
        return true;
    }

    @Override
    public UserDto getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(!(user.isPresent())) return null;
        return modelMapper.map(user.get(),UserDto.class);
    }

    @Override
    public List<UserDto> getUserByFiltred(UserDto filter) {
        List<User> users = userRepository.findByFilter(filter.getFullName(),filter.getEmail());
        List<UserDto> userDtos = users.stream().map(user->modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
        return userDtos;
    }
}
