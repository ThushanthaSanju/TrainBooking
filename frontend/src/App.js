import "./App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

import ViewShcedules from "../src/componets/ViewShcedules";
import AddShcedules from "../src/componets/AddSchedules";
import ViewBooking from "../src/componets/ViewBooking";
import AddBooking from "../src/componets/AddBookings"
import ViewUserSchedule from "../src/componets/ViewUserSchedules";

import AdminNavBar from "../src/componets/Navbar/AdminNavbar";
import NavBar from "../src/componets/Navbar/NavBar";
import Login from "../src/componets/Login";
import Profile from "../src/componets/UserProfile";
import AddUsers from "../src/componets/AddUser";
import ViewUsers from "../src/componets/ViewAdminUsers";
import ViewAgentUser from "../src/componets/ViewAgentUsers";
import AddAgentUser from "../src/componets/AddAgentUser";



function App() {
  return (
    <div>
      <Router>
        {localStorage.getItem("userType") === "admin" ? (
          <AdminNavBar />
        ) : (
          <NavBar />
        )}
        <section>
          <Routes>
            <Route path="/ViewSchedule" element={<ViewShcedules/>} exact/>
            <Route path="/addSchedule" element={<AddShcedules/>} exact/>
            <Route path="/ViewBooking" element={<ViewBooking/>} />
            <Route path="/AddBooking" element={<AddBooking/>} />
            <Route path="/ViewUserSchedule" element={<ViewUserSchedule/>} />
            <Route path="/profile" element={<Profile/>} />
            <Route path="/AddadminUser" element={<AddUsers/>} />
            <Route path="/ViewAdminUser" element={<ViewUsers/>} />
            <Route path="/ViewAgentUser" element={<ViewAgentUser/>} />
            <Route path="/AddAgentUser" element={<AddAgentUser/>} />


            <Route path="/" element={<Login/>} exact/>

          </Routes>
        </section>
        {/* <Footer /> */}
      </Router>
    </div>
  );
}

export default App;