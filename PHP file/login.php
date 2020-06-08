<?php

if ($_SERVER['REQUEST_METHOD']=='POST') {
 $email = $_POST['Username'];
    $password = $_POST['Password'];
   
define("DB_SERVER", "");
define("DB_USER", "");
define("DB_PASSWORD", "");
define("DB_DATABASE", "");

$conn = mysqli_connect(DB_SERVER , DB_USER, DB_PASSWORD, DB_DATABASE);
$query = "SELECT * FROM userdetails WHERE Username = '".$email."' AND Password = '".$password."'" ;
    $response = mysqli_query($conn, $query);

    $result = array();
    $result['login'] = array();
    
        
        $row = mysqli_fetch_assoc($response);

        if (mysqli_num_rows($response) == 1) {
            
          

            $result['success'] = "1";
            $result['message'] = "success";
			header('Content-Type: application/json');
            echo json_encode($result);

            mysqli_close($conn);

        } else {

            $result['success'] = "0";
            $result['message'] = "error";
			header('Content-Type: application/json');
            echo json_encode($result);

            mysqli_close($conn);

        }

    

}

?>