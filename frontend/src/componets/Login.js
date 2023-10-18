import { useNavigate } from "react-router-dom";
import InputField from "./password";
import {
  Avatar,
  Button,
  Paper,
  Grid,
  Typography,
  Container,
} from "@material-ui/core";
import DirectionsBus from "@material-ui/icons/DirectionsBus";
import React, { useState } from "react";
import loginStyle from "./loginStyle";
import axios from "axios";
import { API_URL } from "../utils/constants";

/**
 * inisial form input state
 * @type {{ email: string, password: string}}
 */
const initialState = { email: "", password: "" };

/**
 * sign in and sign up component
 * @returns {*}
 * @constructor
 */
const AdminSignIn = () => {
  /**
   * import variable
   * @type {*}
   */
  const classes = loginStyle();
  let navigate = useNavigate();

  /**
   * states
   */
  const [showPassword, setShowpassword] = useState(false);
  const [isSignUp, setSignUp] = useState(false);
  const [formData, setFormData] = useState(initialState);
  const [data, setData] = useState([]);
  const [errors, setErrors] = useState(initialState);

  /**
   * password visibility togle
   */
  const handleShowPass = () => setShowpassword((prevShowPass) => !prevShowPass);

  /**
   * form submit
   * @param e
   */
  const onSubmit = async (e) => {
    e.preventDefault();
    console.log(formData);
    try {
      await axios
        .post(`${API_URL}/api/User/webSignin`, formData)
        .then((res) => {
          localStorage.setItem("token", res.data.token);
          if (res.data.admin) {
            localStorage.setItem("userType", "admin");
          }
        });
      try {
        const { data } = await axios.get(
          `${API_URL}/api/User/UserByEmail/${formData.email}`
        );
        setData(data);
        console.log(data.name);
        localStorage.setItem("user", JSON.stringify(formData));
        setData(null);
        console.log(data);
      } catch (error) {
        console.log(error);
      }
    //   if(data.type = 1){
        if(localStorage.getItem("userType")){
        navigate("/ViewSchedule");
        }else{
            navigate("/ViewUserSchedule");
        }
    //   }else if(data.type = 2){
    //     navigate("/ViewUserSchedule");
    //   }
      console.log(formData);
      window.location.reload();
    } catch (error) {
      // error.response && setErrorMsg(error.response.data);
      console.log(error);
    }
  };

  /**
   * on text field value change
   * @param e
   */
  const onchange = (e) => {
    e.preventDefault();
    const { name, value } = e.target;
    switch (name) {
      case "email":
        errors.email =
          value.length <= 0 ? "Email Can not be empty! Ex:- example@gmail.com" : "";
        break;
      case "password":
        errors.password = value.length <= 0 ? "Password can not be empty!" : "";

        break;
      default:
        break;
    }
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  return (
    <Container component="main" className="container" maxWidth="md">
      <Paper className={classes.paper} maxWidth="mdx" elevation={3}>
        <div>
          <Avatar className={classes.avatar}>
            <DirectionsBus />
          </Avatar>
          <Typography variant="h5">Sign In</Typography>
        </div>
        <form className={classes.form} onSubmit={onSubmit}>
          <Grid container spacing={2}>
            <InputField
              name="email"
              label="Email"
              handleOnchange={onchange}
              type="text"
            />
            {errors.email.length > 0 && (
              <span className="error">{errors.email}</span>
            )}

            <InputField
              name="password"
              label="Password"
              handleOnchange={onchange}
              type={showPassword ? "text" : "password"}
              handleShowPass={handleShowPass}
            />
            {errors.email.length > 0 && (
              <span className="error">{errors.password}</span>
            )}
          </Grid>

          <Button
            className={classes.submit}
            type="submit"
            fullWidth
            variant="contained"
            color="secondary"
          >
            {isSignUp ? "Sign Up" : "Sign In"}
          </Button>
          <hr />
        </form>
      </Paper>
    </Container>
  );
};

export default AdminSignIn;