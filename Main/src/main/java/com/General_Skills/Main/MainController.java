package com.General_Skills.Main;

import com.General_Skills.Main.Constants.ConstantVariables;
import com.General_Skills.Main.Models.*;
import com.General_Skills.Main.Users.TokenStore;
import com.General_Skills.Main.Users.User;
import com.General_Skills.Main.Users.UserRepository;
import com.General_Skills.Main.service.Notification;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.General_Skills.Main.service.FileUploadService;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller // This means that this class is a Controller
@RequestMapping(path="/main")  // This means URL's start with /main (after Application path)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MainController {


    @Autowired
    UserRepository userRepository;

    //Find User
    @CrossOrigin(origins = ConstantVariables.WebSiteURL, allowedHeaders = "*")
    @GetMapping(path = "/user/find/{username}")
    public @ResponseBody
    User getUser(@PathVariable String username) {

            User user = userRepository.findByUserName(username);
            //System.out.println("User Get Request recieved and sent!");

            return user;
    }

    //GetHighScore
    @CrossOrigin(origins = ConstantVariables.WebSiteURL, allowedHeaders = "*")
    @GetMapping(path = "/highScore5")
    public @ResponseBody
    User[] highScoreList(){
        User[] UserList = userRepository.findByUHighScore();
        return UserList;
    }


    //Create user
    @CrossOrigin(origins = ConstantVariables.WebSiteURL, allowedHeaders = "*")
    @PostMapping(path = "/user/create/{username}/{password}")
    public @ResponseBody
    String createUser(@PathVariable String username, @PathVariable String password){
        if (getUser(username) == null)
        {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            userRepository.save(newUser);

            return "user created successfully";
        }
        return "User already exists";
    }
    //create a new user:
    @CrossOrigin(origins = ConstantVariables.WebSiteURL, allowedHeaders = "*")
    @PostMapping(path = "/user/createUser2")
    public @ResponseBody
    String createUser2(@RequestBody CreateUserModel createUser)
    {
        if ((userRepository.findByUserName(createUser.getUsername())) == null)
        {
            //System.out.println(createUser);
            User newUser = new User();
            newUser.setUsername(createUser.getUsername());
            newUser.setPassword(createUser.getPassword());
            newUser.setHighScore("9999");
            newUser.setUserData(65535);
            userRepository.save(newUser);
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH.mm.ss");
            LocalDateTime myObj = LocalDateTime.now();
            String timeStamp = myObj.format(myFormatObj).toString();
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("                                " + "New Account!");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("                     New User Created: " + newUser.getUsername() + " At: " + timeStamp);
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("                                ");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

            return "user created";
        }
        else
        {
            return "user exists";
        }
    }
    //edit user details
    @CrossOrigin(origins = ConstantVariables.WebSiteURL, allowedHeaders = "*")
    @PostMapping(path = "/user/update")
    public @ResponseBody
    String updatePassword(@RequestBody UpdatePasswordModel updateObject) {
        //System.out.println(updateObject);
        User user = userRepository.findByUserName(updateObject.getUsername());
           if (user.getPassword().equals(updateObject.getPassword())){
               userRepository.updateUser(user.getId(),updateObject.getNewPassword());
               System.out.println("Meg used="+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/(1000*1000)+"M");
               return "Password changed from " + updateObject.getPassword() + " to " + updateObject.getNewPassword();
        }
        return "password incorrect it should have been " + user.getPassword();

    }
    //UpdateScore
    @CrossOrigin(origins = ConstantVariables.WebSiteURL, allowedHeaders = "*")
    @PostMapping(path = "/user/submit")
    public @ResponseBody
    String updateLeaderBoard(@RequestBody UpdateScoreModel updateScoreModel)
    {

        User user = userRepository.findByUserName(updateScoreModel.getUsername());

        if(Float.parseFloat(user.getHighScore()) >= Float.parseFloat(updateScoreModel.getHighScore()))
        {

            userRepository.updateHighScore(user.getId(),updateScoreModel.getHighScore(),updateScoreModel.getHighScoreName());
            return "leaderboard updated";
        }
        else
        {
            //System.out.println("old high score was higher: "+ user.getHighScore());
            return "you suck";

        }

    }



    //U S E R       L O G       I N
    @CrossOrigin(origins = ConstantVariables.WebSiteURL, allowedHeaders = "*")
    @PostMapping(path = "/user/logIn")
    public @ResponseBody
    String generateToken(@RequestBody GetTokenModel createToken)
    {
        User user = userRepository.findByUserName(createToken.getUsername());
        if (user == null)
        {
            return "user does not exist"; //lets unity know that login failed
        }
        else if (user.getPassword().equals(createToken.getPassword()))
        {
            //System.out.println(user);
            String token = TokenStore.getInstance().putToken(createToken.getUsername());
            //System.out.println(createToken + "generated token: "+ token);
            user.setToken(token);
            userRepository.save(user);
            return token;
        }
        else
        {
            return ("Password incorrect cannot login");
        }

    }
    // A U T H E N T I C A T E      T O K E N
    //Recieve username + token, return string ok if token valid and matches server, error if expired
    @CrossOrigin(origins = ConstantVariables.WebSiteURL, allowedHeaders = "*")
    @PostMapping(path = "/user/authCheck")
    public @ResponseBody
    String authCheck(@RequestBody TokenOKModel tokenOK)
    {
        User user = userRepository.findByUserName(tokenOK.getUsername());
        if (user != null)
        {
            String localToken = user.getToken();
            String recievedToken = tokenOK.getToken();
            if (localToken.equals(recievedToken) && (TokenStore.getInstance().getUsername(recievedToken) != null))
            {
                //System.out.println("Meg used="+(Runtime.getRuntime().totalMemory()-
                       // Runtime.getRuntime().freeMemory())/(1000*1000)+"M");
                return "ok";
            }
            else return "authentication token expired";
        }
        else return "username not found";
    }

    //F i l e   U p l o a d   t o   d e f a u l t    l o c a t i o n
    @Autowired
    FileUploadService fileUploadService;
    @CrossOrigin(origins = ConstantVariables.WebSiteURL, allowedHeaders = "*")
    @PostMapping(path = "/user/upload/{username}/{category}/{subCategory}")

    void uploadFile(@RequestParam("file") MultipartFile file, @PathVariable String username, @PathVariable String category, @PathVariable String subCategory) throws IOException {
        //System.out.println("request made for upload");
        String fileName = fileUploadService.uploadFile(file, username, category, subCategory);

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("                                " + "New Upload!");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("                                                                                    ");
        System.out.println("                          " +username + " //" + category + "//"+ subCategory);
        System.out.println("                                                                                    ");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

    }



    //Move the uploaded file to user location
    // Send a JSON packet directly after uplioading the file containing the username, token, Location where the file should go, and the name for the file.
    //the api will move the file to specific directory and name it appropriatly.
    @CrossOrigin(origins = ConstantVariables.WebSiteURL, allowedHeaders = "*")
    @PostMapping(path = "/user/moveFile")
    public @ResponseBody
    String moveFile(@RequestBody FileMoveModel fileMove) throws IOException {

        User user = userRepository.findByUserName(fileMove.getUsername());

        //System.out.println(user.getToken());
        //System.out.println(fileMove.getToken());
        if (user == null)
        {
            return ("error username does not match database");
        }

        else{
            String localToken = user.getToken();
            String recievedToken = fileMove.getToken();
            if (localToken.equals(recievedToken)){
                String path = "J:\\project\\" + user.getUsername() + "\\" + fileMove.getLocation() + "\\" + fileMove.getFileName();

                Path path1 = Paths.get(path);
                Files.createDirectories(path1);


                //do file moving with new path: path
                File sourceFile = new File("D:\\project\\uploadFile\\testFile");
                File destinationFile = new File(path);
                Files.copy(sourceFile.toPath(),destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                //System.out.println(destinationFile.toPath());
                //System.out.println("File Successfully Uploaded");
                return("file move request accepted, moving now . . . ");
            }else{
                return ("error token missmatch, please log in agian");
            }

        }

    }
    @CrossOrigin(origins = ConstantVariables.WebSiteURL, allowedHeaders = "*")
    @GetMapping(path = "/requestData/{username}")
    public @ResponseBody
    String playerProgress(@PathVariable String username){

        User user = userRepository.findByUserName(username);
        int m = user.getUserData();
        String n = Integer.toString(m);
        //int n = Integer.parseInt(m);
        return n;
    }
    //update a users data
    @CrossOrigin(origins = ConstantVariables.WebSiteURL, allowedHeaders = "*")
    @GetMapping(path = "/admin/updateData/{username}/{data}")
    public @ResponseBody
    String updatePlayerData(@PathVariable String username,@PathVariable String data){

        User user = userRepository.findByUserName(username);

        //user.setUserData(Integer.parseInt(data));
        //System.out.println(data);

        userRepository.updatePlayerData(user.getId(),Integer.parseInt(data));

       // int m = user.getUserData();
        //int n = Integer.parseInt(m);
        return data;
    }


    @CrossOrigin(origins = ConstantVariables.WebSiteURL, allowedHeaders = "*")
    @GetMapping(path = "/admin/updateData2/{username}/{cat}/{subCat}")
    public @ResponseBody
    String updatePlayerData2(@PathVariable String username,@PathVariable Integer cat, @PathVariable Integer subCat) {


        int score;
        int power = subCat -1;
        int subCatPower2 = (int) Math.pow(2, power);
        switch (cat) {
            case 1:
                score = 32768 / subCatPower2;
                break;
            case 2:
                score = 2048 / subCatPower2;
                break;
            case 3:
                score = 128 / subCatPower2;
                break;
            case 4:
                score = 8 / subCatPower2;
                break;
            default:
            {
                score = 0;
            }}


        User user = userRepository.findByUserName(username);
        int newData = user.getUserData();
        newData += score;
        userRepository.updatePlayerData(user.getId(), newData);
        String result = Integer.toBinaryString(newData);
        return "score updated to:" + result;
    }
    @CrossOrigin(origins = ConstantVariables.WebSiteURL, allowedHeaders = "*")
    @GetMapping(path = "/ipCheck")
    public @ResponseBody
    String ipCheck() throws AWTException {
        //System.out.println("request for ok got");
        String ok = "ok";
        return "ok";

    }





}






