import React, { useRef, useEffect } from "react";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import Typography from "@material-ui/core/Typography";
import { makeStyles } from "@material-ui/core/styles";
import Alert from "@material-ui/lab/Alert";
import Snackbar from "@material-ui/core/Snackbar";

import axios from "axios";

import { API_URL } from "../utils/constants";
import { Row, Container, Col } from "react-bootstrap";
import {} from "@material-ui/core";
import "bootstrap/dist/css/bootstrap.min.css";
import { useNavigate, useLocation } from "react-router-dom";

import img1 from '../images/acimg.png'


const useStyles = makeStyles((theme) => ({
  leftMinus: {
    marginLeft: "-20",
    display: "flex",
    alignContent: "center",
    justifyContent: "center",
    marginBottom: "30px",
  },
  info: {
    display: "flex",
    alignContent: "center",
    justifyContent: "center",
    marginBottom: "30px",
  },
  dataContainer: {
    backgroundColor: "#ffffff",
    margin: "60px 0px 20px 0px",
    width: "100%",
  },
  imageContainer: {
    height: "300px",
    width: "300px",
    margin: "auto auto auto auto ",
    padding: "0px",
  },
  btnGroup: {
    display: "flex",
    "& > *": {
      margin: theme.spacing(2),
    },
    marginBottom: "100px",
    paddingBottom: "100px",
  },
  textField: {
    height: "40px",
    margin: "10px",
  },
  textarea: {
    height: "80px",
    margin: "10px",
  },
  image: {
    backgroundImage:
      "url(https://s3.amazonaws.com/ultrawidewallpapers/86.jpg)",
    backgroundRepeat: "no-repeat",
    backgroundColor:
      theme.palette.type === "light"
        ? theme.palette.grey[50]
        : theme.palette.grey[900],
    backgroundSize: "cover",
    backgroundPosition: "center",
    height: "250px",
    width: "100%",
  },
  formContainer: {
    width: "100%",
  },
}));

const Profile = (props) => {
  let history = useNavigate();
  const location = useLocation();
  const { useState } = React;
  const [data, setData] = useState([]);
  const classes = useStyles();
  const localUser = JSON.parse(localStorage.getItem("user")) || null;
  let [user, setUser] = useState(localUser);
  const [userData, setUserData] = useState(null);

  useEffect(() => {
    setUser(JSON.parse(localStorage.getItem("user")));
    // console.log("data " + user.formData.eid);
  }, [location]);

  useEffect(() => {
    const getUser = async () => {
      try {
        const email = user.formData.email;
        const { data } = await axios.get(
          `${API_URL}/api/User/UserByEmail/${email}`,
          // {
          //   headers: {
          //     Authorization: `Bearer ${localStorage.getItem('token')}`,
          //   },
          // }
        );
        setErrorMsg("");
        setData(data);
        setUserData(data[0]);
      } catch (error) {
        error.response && setErrorMsg(error.response.data);
        console.log(error);
      }
    };
    getUser();
  }, [location]);

  const [errorMsg, setErrorMsg] = useState("");
  const [successMsg, setSuccessMsg] = useState("");
  const [openErr, setOpenErr] = useState(false);
  const [openSucc, setOpenSucc] = useState(false);


  // const handleClick = () => {
  //   setOpen(true);
  // };

  const handleClose = (event, reason) => {
    if (reason === "clickaway") {
      return;
    }

    setOpenErr(false);
    setOpenSucc(false);
  };

  return (
    <div style={{ height: "100%" }}>
      <Container className={classes.image}>
        <Container className={classes.image}>
          <Row>
            <Col md="4"></Col>
            <Col md="4" className="mt-5 ml-5 align-items-center text-white">
              <Row className={classes.imageContainer}>
                <img
                  className="rounded-circle  "
                  src={img1}
                  width="100px"
                  height="250px"
                ></img>
              </Row>
            </Col>
            <Col md="3"></Col>
          </Row>
        </Container>
        <Container className={classes.formContainer}>
          <div className={classes.dataContainer}>
            <div className={classes.leftMinus}>
              <Typography component="h1" variant="h5">
                {/* User ID : {user.formData.eid} */}
              </Typography>
            </div>

            <form validate>
              <div>
                <Snackbar
                  open={openErr}
                  autoHideDuration={6000}
                  onClose={handleClose}
                >
                  <Alert onClose={handleClose} severity="error">
                    {errorMsg}
                  </Alert>
                </Snackbar>
                <Snackbar
                  open={openSucc}
                  autoHideDuration={6000}
                  onClose={handleClose}
                >
                  <Alert onClose={handleClose} severity="success">
                    {successMsg}
                  </Alert>
                </Snackbar>
              </div>
              <div className={classes.info}>
                <TextField
                  className={classes.textField}
                  variant="outlined"
                  margin="normal"
                  required
                  fullWidth
                  id="name"
                  label="User Name"
                  name="name"
                  autoComplete="name"
                  autoFocus
                  value={userData ? userData.name : "" || ""}
                />
                <TextField
                  className={classes.textField}
                  variant="outlined"
                  margin="normal"
                  required
                  fullWidth
                  id="email"
                  label="E- mail address"
                  name="email"
                  autoComplete="email"
                  autoFocus
                  value={userData ? userData.email : "" || ""}
                />
              </div>

              <div className={classes.info}>
                <TextField
                  className={classes.textarea}
                  style={{ margin: "10 20 10 20" }}
                  variant="outlined"
                  margin="normal"
                  required
                  fullWidth
                  multiline
                  id="address"
                  label="address"
                  name="address"
                  autoComplete="address"
                  autoFocus
                  value={userData ? userData.address : "" || ""}
                />
              </div>
              <div className={classes.info}>
                <TextField
                  className={classes.textField}
                  variant="outlined"
                  margin="normal"
                  required
                  fullWidth
                  id="contact"
                  label="Contact Number"
                  name="contact"
                  autoComplete="contact"
                  autoFocus
                  value={userData ? userData.contact : "" || ""}
                />
                <TextField
                  className={classes.textField}
                  variant="outlined"
                  margin="normal"
                  required
                  fullWidth
                  id="password"
                  label="Password"
                  name="password"
                  autoComplete="password"
                  autoFocus
                  value={userData ? userData.password : "" || ""}
                />
              </div>
              

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
                  type="submit"
                  fullWidth
                  variant="contained"
                  color="primary"
                  className={classes.sub}
                >
                  Save
                </Button>

                <Button
                  type="reset"
                  fullWidth
                  variant="contained"
                  color="secondary"
                  className={classes.clear}
                >
                  Clear
                </Button>
              </div>
            </form>
          </div>
        </Container>
      </Container>
    </div>
  );
};

export default Profile;