import React, { useState, useEffect  } from "react";
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
import Select from '@material-ui/core/Select';
import MenuItem from "@material-ui/core/MenuItem";
import { red } from "@material-ui/core/colors";


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
      "url(https://images.unsplash.com/photo-1532105956626-9569c03602f6?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1887&q=80)",
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
  selt:{
    text: {
      color: "#000000"
    }
  }
}));

export default function AddReservation() {
  let history = useNavigate();
  const classes = useStyles();
  const [open, setOpen] = useState(false);
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [arrivedTime, setArrivedTime] = useState("");
  const [departureTime, setDepartureTime] = useState("");
  const [priceForOneSeat, setPriceForOneSeat] = useState("");
  const [from, setFrom] = useState("");
  const [to, setTo] = useState("");
  const [numberOfSeat, setNumberofSeat] = useState("");
  const [date, setDate] = useState("");

  const [errorMsg, setErrorMsg] = useState("");
  const [successMsg, setSuccessMsg] = useState("");
  const [errors, setErrors] = useState({
    email: "",
    name: "",
    from: "",
  });

  const handleName = (e) => {
    setName(e.target.value);
  };
  const handleEmail = (e) => {
    setEmail(e.target.value);
  };


  const handleArrivedTime = (e) => {
    setArrivedTime(e.target.value);
  };

  const handleDepartureTime = (e) => {
    setDepartureTime(e.target.value);
  };

  const handlePriceForOneSeat = (e) => {
    setPriceForOneSeat(e.target.value);
  };

  const handleFrom = (e) => {
    setFrom(e.target.value);
  };

  const handleTo = (e) => {
    setTo(e.target.value);
  };

  const handleNumberofSeat = (e) => {
    setNumberofSeat(e.target.value);
  };

 

  const handleDate = (e) => {
    setDate(e.target.value);
  };

  const handleClose = (event, reason) => {
    if (reason === "clickaway") {
      return;
    }
    setOpen(false);
  };
  const clear = () => {
    setselectedCategorias("")
    setName("");
    setEmail("");
    setArrivedTime("");
    setPriceForOneSeat("");
    setFrom("");
    setDepartureTime("");
    setTo("");
    setNumberofSeat("");
    setDate("");
  };

  //validations for the form
  const validate = () => {
    let errors = {};
    let isValid = true;

    // if (name != null) {
    //   isValid = false;
    //   errors["name"] = "Please select Train";
    // }
    // if (from.length !== 6) {
    //   isValid = false;
    //   errors["from"] = "Please enter valid book code";
    // }
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
  const [categorias, setCategorias] = useState([]);
  const [selectedCategorias, setselectedCategorias] = useState("");


  useEffect(() => {
    const getCategorias = async () => {
      const res = await fetch(`${API_URL}/api/TrainShedule/GetTrain/`, {
        method: "GET",
        headers: { "Content-Type": "application/json" }
      });
      //console.log(res);
      const response = await res.json();
      setCategorias(response);
    };
    getCategorias();
  }, []);

  const [TransferData, setTransferData] = useState([])
  const [ContactValue, setContactValue] = useState('')

  const handleChange = (event) => {
    setContactValue(event.target.value);
  };

  const TokenData = () => {
    axios.get(`${API_URL}/api/TrainShedule/GetTrain/`).then(function (res) {
      try {
        var result = res.data;
        console.log(result)
        setTransferData(result)
      }
      catch (error) {
        console.log(error)
      }
    })
  }

  useEffect(() => {
    TokenData()
  }, []);


  //submit the form
  const onSubmit = () => {
    if (validate()) {
      setOpen(true);
      const reservation = {
        name: selectedCategorias,
        email:email,
        arrivedTime: arrivedTime,
        departureTime: departureTime,
        priceForOneSeat: priceForOneSeat,
        from:from,
        to: to,
        numberOfSeat: numberOfSeat,
        date: date,

      };
      axios
        .post(`${API_URL}/api/TrainBooking/TrainBookingInsert`, reservation, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`,
          },
        })
        .then((res) => {
          if (res.data.success) {
            setselectedCategorias("")
            setName("");
            setEmail("");
            setArrivedTime("");
            setPriceForOneSeat("");
            setFrom("");
            setDepartureTime("");
            setTo("");
            setNumberofSeat("");
            setDate("");
            setSuccessMsg("Successfully inserted");
          // } else if(res.data.error){
          //   setErrorMsg("Maximum Bookings Exceeded");
          }else{
            setErrorMsg("Please try again");
          }
          // if(res.data.error){
          //   setErrorMsg("Maximum Bookings Exceeded");
          // }
        })
        .catch((err) => {
          if (err.response) {
            // Handle error response from the server
            const errorMessage = err.response.data.error;
            setErrorMsg("Maximum Bookings Exceeded");
            // Display the error message to the user in your frontend
          } else {
            // Handle other types of errors, e.g., network issues
          }
        });
    }
  };
  const today = new Date(); // Get the current date
const maxDate = new Date(today);
maxDate.setDate(today.getDate() + 30);

  return (
    
    <Grid container component="main" className={classes.root}>
      <CssBaseline />
      <Grid item xs={false} sm={4} md={6} className={classes.image} />
      <Grid item xs={12} sm={8} md={6} component={Paper} elevation={6} square>
        <div className={classes.paper}>
          <Typography component="h1" variant="h5">
            Add Booking
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
          style={{ width: "100%", color: "#000000" }}
            labelId="demo-simple-select-filled-label"
            variant="outlined"
            margin="normal"
            fullWidth
            id="name"
            label="Train Name"
            autoFocus
            required
            value={selectedCategorias}
            select
            onChange={(e) => setselectedCategorias(e.target.value)}
          >
          <MenuItem value="">
            <em>None</em>
          </MenuItem>
            {categorias.map((categoria) => (
          <MenuItem key={categoria.id} value={categoria.name} className={classes.selt} >{categoria.name}</MenuItem>
        ))}
      </TextField>
{/* 
      <Select
                labelId="Contact Select"
                id="demo-simple-select"
                value={ContactValue}
                label='Select Contact'
                onChange={handleChange}
              >
                {TransferData.map((data) => (
                  <MenuItem key={data.id} value={data.id}>{data.name}</MenuItem>
                ))}
              </Select> */}
          {/* <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="name"
            label="Train Name"
            name="name"
            autoComplete="name"
            value={name}
            onChange={(e) => handleName(e)}
            autoFocus
          /> */}
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
          type="time"
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="arrivedTime"
            label="Arrived Time"
            name="arrivedTime"
            autoComplete="arrivedTime"
            value={arrivedTime}
            onChange={(e) => handleArrivedTime(e)}
            autoFocus
          />
          {/* {errors.arrivedTime && (
            <span className="error">{errors.arrivedTime}</span> 
          )} */}
          <TextField
          type="time"
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="departureTime"
            label="Departure Time"
            name="departureTime"
            autoComplete="departureTime"
            value={departureTime}
            onChange={(e) => handleDepartureTime(e)}
            autoFocus
          />
          {/* {errors.departureTime ? <span className="error">{errors.departureTime}</span> : <></>} */}
          {/* <TextField
          type="number"
            variant="outlined"
            margin="normal"
            required
            InputProps={{ inputProps: { min: 0 } }}
            fullWidth
            id="priceForOneSeat"
            label="Price For One Seat"
            name="priceForOneSeat"
            autoComplete="priceForOneSeat"
            value={priceForOneSeat}
            onChange={(e) => handlePriceForOneSeat(e)}
            autoFocus
          /> */}
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="from"
            label="From"
            name="from"
            autoComplete="from"
            value={from}
            onChange={(e) => handleFrom(e)}
            autoFocus
          />
          {/* {errors.from ? (
            <span className="error">{errors.from}</span>
          ) : (
            <></>
          )} */}

        <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="to"
            label="To"
            name="to"
            autoComplete="to"
            value={to}
            onChange={(e) => handleTo(e)}
            autoFocus
          />

        <TextField
        type="number"
            variant="outlined"
            margin="normal"
            required
            fullWidth
            InputProps={{ inputProps: { min: 0 } }}
            id="numberOfSeat"
            label="Number Of Seat"
            name="numberOfSeat"
            autoComplete="numberOfSeat"
            value={numberOfSeat}
            onChange={(e) => handleNumberofSeat(e)}
            autoFocus
          />
                 
          <TextField
          type="date"
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="date"
            label="date"
            name="date"
            defaultValue={new Date().toISOString().slice(0, 10)}
            autoComplete="date"
            inputProps={{
              min: new Date().toISOString().slice(0, 10),
              max: "2023-12-12",
              // max: maxDate,
            }}
            value={date}
            onChange={(e) => handleDate(e)}
            autoFocus
          />

          <div className={classes.btnGroup}>
            {/* <Button
              id="btnBack"
              type="button"
              onClick={history('/ViewBooking')}
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