import React, { useEffect, useState } from "react";
import MaterialTable from "material-table";
import Button from "@material-ui/core/Button";
import axios from "axios";
import { API_URL } from "../utils/constants.js";

// import Modal from "./Modal";

export default function ViewSchedules() {
  const [data, setData] = useState([]);
  const [reportData, setReportData] = useState([]);
  useEffect(() => {
    axios
      .get(`${API_URL}/api/TrainShedule/TrainSheduleGetAll`, {
        // headers: {
        //     "Content-Type": "application/json",
        // },
      })
      .then((res) => {
        setData(res.data);
      });
  }, []);

  const api = axios.create({
    baseURL: `${API_URL}`,
  });

  const handleRowUpdate = (newData, oldData, resolve) => {
    //validation
    let errorList = [];
    if (newData.title === "") {
      errorList.push("Please enter title");
    }
    if (newData.author === "") {
      errorList.push("Please enter author");
    }
    if (newData.publisher === "") {
      errorList.push("Please enter publisher");
    }
    if (newData.noOfCopies === "") {
      errorList.push("Please enter noOfCopies");
    }

    if (errorList.length < 1) {
      api
        .put("/api/TrainShedule/" + newData._id, newData, {
          // headers: {
          //   Authorization: `Bearer ${localStorage.getItem('token')}`,
          // },
        })
        .then((res) => {
          const dataUpdate = [...data];
          const index = oldData.tableData.id;
          dataUpdate[index] = newData;
          setData([...dataUpdate]);
          resolve();

        })
        .catch((error) => {
          resolve();
        });
    } else {
      resolve();
    }
  };

   //delete details
   const handleRowDelete = (oldData, resolve) => {
    axios
      .delete(`${API_URL}/api/TrainShedule/` + oldData._id, {
        // headers: {
        //   Authorization: `Bearer ${localStorage.getItem("token")}`,
        // },
      })
      .then((res) => {
        const dataDelete = [...data];
        const index = oldData.tableData.id;
        dataDelete.splice(index, 1);
        setData([...dataDelete]);
        resolve();

      })
      .catch((error) => {

        resolve();
      });
  };

  let fields = [
    { title: "Train Name", field: "name" },
    { title: "Arrived Time", field: "arrivedTime" ,type: "time"},
    { title: "Departure Time", field: "departureTime" ,type: "time"},
    { title: "Price For One Seat", field: "priceForOneSeat", type:"numeric" },
    { title: "From", field: "from" },
    { title: "To", field: "to" },
    { title: "Status", field: "status" , lookup: {"true":"true", "false":"false"}},
  ];

  return (
    <div>
      <br />
      <h1 id="h12" align="center">
        Train Schedules
      </h1>
      <div className="tbl2" style={{ width: "90%", margin:"auto"}}>
        <MaterialTable
        title={
            <>
              {/* <Button
                id="btnAdd"
                variant="contained"
                color="primary"
                href="/addSchedule"
              >
                Add Schedules
              </Button> */}
            </>
          }
          columns={fields}
          data={data}
        //   editable={{
        //     onRowUpdate: (newData, oldData) =>
        //       new Promise((resolve, reject) => {
        //         handleRowUpdate(newData, oldData, resolve);
        //       }),

        //     onRowDelete: (oldData) =>
        //       new Promise((resolve, reject) => {
        //          handleRowDelete(oldData, resolve);
        //       }),
        //   }}
          options={{
            headerStyle: {
                // action: false,
              backgroundColor: "rgb(72, 138, 199)",
              color: "rgba(0, 0, 0)",
            },
            actionsColumnIndex: -1,
          }}
        />
      </div>
      {/* <Modal
        title="Send Email "
        openPopup={openModal}
        setOpenPopup={setOpenModal}
      >
        <EmailForm setOpenModal={setOpenModal} emailData={emailData} />
      </Modal> */}
    </div>
  );
}
