(function() {
    'use strict';
    window.addEventListener('load', function() {
// Fetch all the forms we want to apply custom Bootstrap validation styles to
        var forms = document.getElementsByClassName('needs-validation');
// Loop over them and prevent submission
        var validation = Array.prototype.filter.call(forms, function(form) {
            form.addEventListener('submit', function(event) {
                if (form.checkValidity() === false) {
                    event.preventDefault();
                    event.stopPropagation();

                }

                form.classList.add('was-validated');
            }, false);
        });
    }, false);
})();

function validateName() {
    var name=$("#name").val();
    if(name === ""){
        $("#nameErr").html("name is required");
        $("#register").attr("disabled","disabled");

    }
    else if(/^[a-zA-Z ]+$/.test(name)===false){
        $("#nameErr").html("Name is only contain alphabets and space");
        $("#register").attr("disabled","disabled");
    }
    else {
        $("#register").removeAttr("disabled");
        $("#nameErr").html("");

    }
}


function validateEmail() {
    var email=$("#email").val();
    if(email === ""){
        $("#emailErr").html("Email address is require");
        $("#register").attr("disabled","disabled");
    }
    else{
        $("#register").removeAttr("disabled");
        $("#emailErr").html("");
    }
}

function validateMobileNo() {
    var mobile=$("#mobileNo").val();
    if(mobile === ""){
        $("#mobileErr").html("Mobile number is require");
        $("#register").attr("disabled","disabled");
    }
    else if(mobile.match("/^[0-9]{10} $/") === false){
        $("#mobileErr").html("Mobile number contains only digits");
        $("#register").attr("disabled","disabled");
    }
    else if(mobile.length !== 10){
        $("#mobileErr").html("Mobile number must be of 10 digits");
        $("#register").attr("disabled","disabled");
    }
    else{
        $("#register").removeAttr("disabled");
        $("#mobileErr").html("");
    }
}

function validatePassword() {
    var password=$("#password").val();
    var exp=/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,16} $/
    if(password === ""){
        $("#passErr").html("Password is require");
        $("#register").attr("disabled","disabled");
    }
    else if(password.match(exp) === false){
        $("#passErr").html("Password must be between 8 to 16 characters which contains at least one numeric digit, one uppercase and one lower case letter");
        $("#register").attr("disabled","disabled");
    }
    else{
        $("#register").removeAttr("disabled");
        $("#passErr").html("");
    }

}

function validateConfirmPassword() {
    var password=$("#password").val();
    var conPass=$("#confirmPassword").val();
    if(conPass === ""){
        $("#conPassErr").html("Confirm Password is require");
        $("#register").attr("disabled","disabled");
        msg+="Confirm Password is require\n"
        return false;
    }
    else if(conPass !== password){
        $("#conPassErr").html("Confirm password must be same as password");
        $("#register").attr("disabled","disabled");
        return false;
    }
    else{
        $("#register").removeAttr("disabled");
        $("#conPassErr").html("");
    }
}


function validateId() {
    var id = $("#studentId").val()
    if (id === "") {
        $("#idErr").html("Student Id is required");
        $("#register").attr("disabled","disabled");
    }
    else if (id.match("/^[A-Za-z0-9] $/") === false) {
        $("#idErr").html("Student id is only contains alphabets and numbers");
        $("#register").attr("disabled","disabled");

    }
    else if (id.length !== 10) {
        $("#idErr").html("Student id must be of 10 characters");
        $("#register").attr("disabled","disabled");
    }
    else{
        $("#register").removeAttr("disabled");
        $("#idErr").html("");
    }
}

function validateRollNo() {
    var rollno = $("#rollNo").val();
    if (rollno === "") {
        $("#noErr").html("Roll number is required");
        $("#register").attr("disabled", "disabled");
    } else if (rollno.length !== 5) {
        $("#noErr").html("Roll number must be of 5 characters");
        $("#register").attr("disabled", "disabled");
    } else if (rollno.match("/^[A-Za-z0-9] $/") === false) {
        $("#noErr").html("Roll number is only contains alphabets and number");
        $("#register").attr("disabled", "disabled");
    }
    else{
        $("#register").removeAttr("disabled");
        $("#noErr").html("");
    }
}

function validateBatch() {
    var batch = $("#batch").val();
    if (batch === "") {
        $("#batchErr").html("Batch is required field");
        $("#register").attr("disabled", "disabled");
    }
    else if (batch.match("/^[0-9-]$/")) {
        $("#batchErr").html("Batch contains only digits and -");
        $("#register").attr("disabled", "disabled");
    }
    else{
        $("#register").removeAttr("disabled");
        $("#batchErr").html("");
    }
}

function validateSem() {
    var sem = $("#sem").val()
    if (sem === "") {
        $("#semErr").html("Semester is require");
        $("#register").attr("disabled", "disabled");
    }
    else if (sem.match("/^[0-9] $/") === false && sem.length !== 1) {
        $("#semErr").html("Semester contains only one digit");
        $("#register").attr("disabled", "disabled");
    }
    else{
        $("#register").removeAttr("disabled");
        $("#semErr").html("");
    }
}


function validateStudent() {
    validateName()
    validateEmail()
    validateMobileNo()
    validatePassword()
    validateConfirmPassword()
    validateId()
    validateRollNo()
    validateBatch()
    validateSem()
}

function validateFaculty() {
    validateName()
    validateEmail()
    validateMobileNo()
    validatePassword()
    validateConfirmPassword()

}