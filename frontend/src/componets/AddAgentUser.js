
import React, { useState } from "react";
import Button from "@material-ui/core/Button";
import CssBaseline from "@material-ui/core/CssBaseline";
import TextField from "@material-ui/core/TextField";
import Paper from "@material-ui/core/Paper";
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import { makeStyles } from "@material-ui/core/styles";
import axios from "axios";

import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";
import Draggable from "react-draggable";
import { useNavigate } from "react-router-dom";
import CheckIcon from "@material-ui/icons/Check";
import ClearIcon from "@material-ui/icons/Clear";
import LocalLibraryIcon from "@material-ui/icons/LocalLibrary";
import { API_URL } from "../utils/constants";
import MenuItem from "@material-ui/core/MenuItem";
function PaperComponent(props) {
  return (
    <Draggable
      handle="#draggable-dialog-title"
      cancel={'[class*="MuiDialogContent-root"]'}
    >
      <Paper {...props} />
    </Draggable>
  );
}

const useStyles = makeStyles((theme) => ({
  root: {
    height: "100vh",
    width: "85%",
    margin: "auto",
    marginTop: "20px",
  },
  alert: {
    width: "100%",
    "& > * + *": {
      marginTop: theme.spacing(2),
    },
  },
  image: {
    backgroundImage:
      "url(https://images.unsplash.com/photo-1523697753094-d89aa5119fc0?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1887&q=80)",
    backgroundRepeat: "no-repeat",
    backgroundColor:
      theme.palette.type === "light"
        ? theme.palette.grey[50]
        : theme.palette.grey[900],
    backgroundSize: "cover",
    backgroundPosition: "center",
  },
  paper: {
    margin: theme.spacing(8, 4),
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
  },
  avatar: {
    margin: theme.spacing(1),
    backgroundColor: theme.palette.secondary.main,
  },
  form: {
    width: "90%", // Fix IE 11 issue.
    marginTop: theme.spacing(1),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
  btnGroup: {
    display: "flex",
    "& > *": {
      margin: theme.spacing(2),
    },
  },
}));

export default function AddReservation() {
  let history = useNavigate();
  const classes = useStyles();
  const [open, setOpen] = useState(false);
  const [fullname, setName] = useState("");
  const [nic, setNic] = useState("");
  const [phone, setPhone] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [type, setType] = useState("");
  const [status, setStatus] = useState("");


  const [errorMsg, setErrorMsg] = useState("");
  const [successMsg, setSuccessMsg] = useState("");
  const [errors, setErrors] = useState({
    nic: "",
    email: "",
    phone: "",
  });

  const handleName = (e) => {
    setName(e.target.value);
  };

  const handleNiC = (e) => {
    setNic(e.target.value);
  };

  const handlePhone = (e) => {
    setPhone(e.target.value);
  };

  const handleEmail = (e) => {
    setEmail(e.target.value);
  };

  const handlePassword = (e) => {
    setPassword(e.target.value);
  };

 

  const handleType = (e) => {
    setType(e.target.value);
  };

  const handleStatus = (e) => {
    setStatus(e.target.value);
  };


  const handleClose = (event, reason) => {
    if (reason === "clickaway") {
      return;
    }
    setOpen(false);
  };
  const clear = () => {
    setName("");
    setNic("");
    setEmail("");
    setPassword("");
    setPhone("");
    setType("");
    setStatus("");
  };

  //validations for the form
  const validate = () => {
    let errors = {};
    let isValid = true;

    if (nic.length !== 10) {
      isValid = false;
      errors["nic"] = "Please enter valid NIC";
    }
    if (phone.length !== 10) {
      isValid = false;
      errors["phone"] = "Please enter valid phone number";
    }
    if (typeof email !== "undefined") {
      var pattern = new RegExp(
        /^(("[\w-\s]+")|([\w-]+(?:\.[\w-]+)*)|("[\w-\s]+")([\w-]+(?:\.[\w-]+)*))(@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$)|(@\[?((25[0-5]\.|2[0-4][0-9]\.|1[0-9]{2}\.|[0-9]{1,2}\.))((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\.){2}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\]?$)/i
      );
      if (!pattern.test(email)) {
        isValid = false;
        errors["email"] = "Please enter valid email address.";
      }
    }
    setErrors(errors);
    return isValid;
  };

  //submit the form
  const onSubmit = () => {
    if (validate()) {
      setOpen(true);
      const reservation = {
        fullname: fullname,
        nic: nic,
        phone: phone,
        email: email,
        password: password,
        status: status,
        type: type,
      

      };
      axios
        .post(`${API_URL}/api/User/userInsert`, reservation, {
        //   headers: {
        //     Authorization: `Bearer ${localStorage.getItem("token")}`,
        //   },
        })
        .then((res) => {
          if (res.data.success) {
            setName("");
            setNic("");
            setEmail("");
            setPassword("");
            setPhone("");
            setType("");
            setStatus("");
            setSuccessMsg("Successfully inserted");
          } else {
            setErrorMsg("Please try again");
          }
        })
        .catch((err) => {
          console.log(err);
        });
    }
  };

  return (
    <Grid container component="main" className={classes.root}>
      <CssBaseline />
      <Grid item xs={false} sm={4} md={6} className={classes.image} />
      <Grid item xs={12} sm={8} md={6} component={Paper} elevation={6} square>
        <div className={classes.paper}>
          <Typography component="h1" variant="h5">
            Add User
          </Typography>
          <div className={classes.alert}>
            <Dialog
              open={open}
              onClose={handleClose}
              PaperComponent={PaperComponent}
              aria-labelledby="draggable-dialog-title"
            >
              <DialogTitle
                style={{
                  cursor: "move",
                  backgroundColor: "#02032b",
                  color: "#ffffff",
                }}
                id="draggable-dialog-title"
              >
                <LocalLibraryIcon /> LMS
              </DialogTitle>
              <DialogContent>
                <DialogContentText>
                  {successMsg !== "" ? (
                    <>
                      <div style={{ color: "#008000" }}>
                        <CheckIcon />
                        {successMsg}
                      </div>
                    </>
                  ) : (
                    <>
                      <div style={{ color: "#aa202b" }}>
                        <ClearIcon />
                        {errorMsg}
                      </div>
                    </>
                  )}
                </DialogContentText>
              </DialogContent>
              <DialogActions>
                <Button onClick={handleClose} color="primary">
                  Ok
                </Button>
              </DialogActions>
            </Dialog>
          </div>
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="fullname"
            label="Full Name"
            name="fullname"
            autoComplete="fullname"
            value={fullname}
            onChange={(e) => handleName(e)}
            autoFocus
          />
          <TextField
          
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="nic"
            label="NIC"
            name="nic"
            autoComplete="nic"
            value={nic}
            onChange={(e) => handleNiC(e)}
            autoFocus
          />
          {errors.nic ? <span style={{color:"#FF0000"}}>{errors.nic}</span> : <></>}
          <TextField
          
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="phone"
            label="Phone Number"
            name="phone"
            autoComplete="phone"
            value={phone}
            onChange={(e) => handlePhone(e)}
            autoFocus
          />
          {errors.phone ? <span style={{color:"#FF0000"}}>{errors.phone}</span> : <></>}
          <TextField
          
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="email"
            label="Email"
            name="email"
            autoComplete="email"
            value={email}
            onChange={(e) => handleEmail(e)}
            autoFocus
          />
          {errors.email ? <span style={{color:"#FF0000"}}>{errors.email}</span> : <></>}
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="password"
            label="Password"
            name="password"
            autoComplete="password"
            value={password}
            onChange={(e) => handlePassword(e)}
            autoFocus
          />
          {errors.password ? (
            <span className="error">{errors.password}</span>
          ) : (
            <></>
          )}

        {/* <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="type"
            label="type"
            name="type"
            autoComplete="type"
            value={type}
            onChange={(e) => handleType(e)}
            autoFocus
          /> */}
                  <TextField
            variant="outlined"
            margin="normal"
            fullWidth
            id="type"
            label="Type"
            autoFocus
            required
            value={type}
            select
            onChange={(e) => handleType(e)}
          >
          <MenuItem value={0}>User</MenuItem>
          <MenuItem value={1}>Agent</MenuItem>
        
        </TextField>

        <TextField
            variant="outlined"
            margin="normal"
            fullWidth
            id="status"
            label="Status"
            autoFocus
            required
            value={status}
            select
            onChange={(e) => handleStatus(e)}
          >
          <MenuItem value={"true"}>True</MenuItem>
          <MenuItem value={"false"}>False</MenuItem>
        
        </TextField>
        
                  {/* <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="status"
            label="Status"
            name="status"
            autoComplete="status"
            value={status}
            onChange={(e) => handleStatus(e)}
            autoFocus
          /> */}
         

          <div className={classes.btnGroup}>
            {/* <Button
              id="btnBack"
              type="button"
              onClick={history.goBack}
              fullWidth
              variant="contained"
              color="primary"
              className={classes.back}
            >
              Back
            </Button> */}

            <Button
              id="btnSave"
              // type="submit"
              fullWidth
              variant="contained"
              color="primary"
              className={classes.sub}
              onClick={() => onSubmit()}
            >
              Save
            </Button>

            <Button
              type="reset"
              fullWidth
              variant="contained"
              color="secondary"
              className={classes.clear}
              onClick={clear}
            >
              Clear
            </Button>
          </div>
        </div>
      </Grid>
    </Grid>
  );
}