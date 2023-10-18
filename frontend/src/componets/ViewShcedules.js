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
    // { title: "ID", field: "_id" },
    { title: "Train Name", field: "name" },
    { title: "Arrived Time", field: "arrivedTime" ,type: "time"},
    { title: "Departure Time", field: "departureTime" ,type: "time"},
    { title: "Price For One Seat", field: "priceForOneSeat", type:"numeric" },
    { title: "From", field: "from" },
    { title: "To", field: "to" },
    { title: "Status", field: "status" , lookup: {"true":"true", "false":"false"}},


    // { title: "Destination", field: "destination" },
    // { title: "Time", field: "time" },
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
              <Button
                id="btnAdd"
                variant="contained"
                color="primary"
                href="/addSchedule"
              >
                Add Schedules
              </Button>
            </>
          }
          columns={fields}
          data={data}
          editable={{
            onRowUpdate: (newData, oldData) =>
              new Promise((resolve, reject) => {
                handleRowUpdate(newData, oldData, resolve);
              }),

            onRowDelete: (oldData) =>
              new Promise((resolve, reject) => {
                 handleRowDelete(oldData, resolve);
              }),
          }}
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


//

// import React, { useState, useEffect } from "react";
// import Table from "material-table";
// import Button from "@material-ui/core/Button";
// import axios from "axios";
// import "./style.css";
// import { API_URL } from "../../utils/constants";
// // import Modal from "./Modal";
// import jsPDF from "jspdf";
// import "jspdf-autotable";

// const Editable = (props) => {
//   const [data, setData] = useState([]);
//   const [inspectionDetails, setinspectionDetails] = useState([]);

//   useEffect(() => {
//     axios
//       .get(`${API_URL}/api/inspection`, {
//         // headers: {
//         //   Authorization: `Bearer ${localStorage.getItem("token")}`,
//         // },
//       })
//       .then((res) => {
//         setData(res.data);
//       });
//     retrieveInspectionDetails();
//   }, []);

//   const retrieveInspectionDetails = () => {
//     setinspectionDetails(null);
//     axios.get(`${API_URL}/api/inspection`, {}).then((res) => {
//       console.log(res.data);
//       res.data.forEach((item) => {
//         let object = {
//           BusNo: item.date,
//           Date: item.bid,
//           Time: item.btime,
//           PassengerId: item.passengerId,
//         };
//         inspectionDetails.push(object);
//       });
//       setinspectionDetails(inspectionDetails);
//     });
//   };

//   //staff member pdf export
//   const exportInspectionPDF = (tableData, type) => {
//     const unit = "pt";
//     const size = "A4"; // Use A1, A2, A3 or A4
//     const orientation = "portrait"; // portrait or landscape
//     const marginLeft = 40;
//     const doc = new jsPDF(orientation, unit, size);
//     doc.setFontSize(15);
//     console.log(tableData);

//     //title of pdf
//     const title = "Inspection report";
//     //headers
//     const headers = [["Bus No", "Date", "Time", "Passenger ID"]];

//     //data
//     if (type == "all") {
//       tableData = inspectionDetails.map((elt) => [
//         elt.BusNo,
//         elt.Date,
//         elt.Time,
//         elt.PassengerId,
//       ]);
//     }
//     let content = {
//       startY: 50,
//       head: headers,
//       body: tableData,
//     };
//     doc.text(title, marginLeft, 40);
//     doc.autoTable(content);
//     //save name
//     doc.save("InspectionReport.pdf");
//   };

//   let fields = [
//     { title: "Bus No", field: "date" },
//     { title: "Date", field: "bid" },
//     { title: "Time", field: "btime" },
//     { title: "PassengerId", field: "passengerId" },
//   ];

//   return (
//     <div>
//       <br />
//       <h1 id="h12" align="center">
//         Inspection Reports
//       </h1>
//       <br />
//       <div className="tbl">
//         <Table
//           title={
//             <>
//               <Button
//                 id="btnAdd"
//                 variant="contained"
//                 color="primary"
//                 type="submit"
//                 onClick={() => exportInspectionPDF(inspectionDetails, "all")}
//               >
//                 GENERATE
//               </Button>
//             </>
//           }
//           columns={fields}
//           data={data}
//           options={{
//             headerStyle: {
//               backgroundColor: "rgb(72, 138, 199)",
//               color: "rgba(0, 0, 0)",
//             },
//             actionsColumnIndex: -1,
//           }}
//         />
//       </div>
//     </div>
//   );
// };

// export default Editable;