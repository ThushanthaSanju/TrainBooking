import React, { useEffect, useState } from "react";
import MaterialTable from "material-table";
import Button from "@material-ui/core/Button";
import axios from "axios";
import { API_URL } from "../utils/constants.js";

// import Modal from "./Modal";

export default function ViewBooking() {
  const [data, setData] = useState([]);
  const [reportData, setReportData] = useState([]);
  useEffect(() => {
    axios
    .get(`${API_URL}/api/User/UserGetAll`, {
       
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
    if (newData.fullname === "") {
      errorList.push("Please enter name");
    }
    if (newData.nic === "") {
      errorList.push("Please enter email");
    }
    if (newData.phone === "") {
      errorList.push("Please enter nic");
    }
    if (newData.email === "") {
      errorList.push("Please enter mobilenumber");
    }
    if (newData.password === "") {
        errorList.push("Please enter arrived time");
    }
   if (newData.status === "") {
        errorList.push("Please enter departure time");
   }
 if (newData.type === "") {
        errorList.push("Please enter price for one seat");
  }
 

  

    if (errorList.length < 1) {
      api
        .put("/api/User/" + newData._id, newData, {
          // headers: {
          //   Authorization: Bearer ${localStorage.getItem('token')},
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
//    const handleRowDelete = (oldData, resolve) => {
//     axios
//       .delete(${API_URL}/api/TrainBooking/ + oldData._id, {
//         // headers: {
//         //   Authorization: Bearer ${localStorage.getItem("token")},
//         // },
//       })
//       .then((res) => {
//         const dataDelete = [...data];
//         const index = oldData.tableData.id;
//         dataDelete.splice(index, 1);
//         setData([...dataDelete]);
//         resolve();

//       })
//       .catch((error) => {

//         resolve();
//       });
//   };

    //delete details
    const handleRowDelete = (oldData, resolve) => {
        axios
          .delete(`${API_URL}/api/User/` + oldData._id, {
            // headers: {
            //   Authorization: Bearer ${localStorage.getItem("token")},
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
    // { title: "ID/QR", field: "_id" },
    { title: "Full Name", field: "fullname" },
    { title: "Nic", field: "nic" },
    { title: "Phone", field: "phone" },
    { title: "Email", field: "email" },
    { title: "Type", field: "type", lookup: {0:"User", 1:"Agent", 2:"Admin"}},
    { title: "Status", field: "status", lookup: {"true":"true", "false":"false"}},
    

    // { title: "Destination", field: "destination" },
    // { title: "Time", field: "time" },
  ];

  return (
    <div>
      <br />
      <h1 id="h12" align="center">
       User List
      </h1>
      <div className="tbl2" style={{ width: "90%", margin:"auto"}}>
        <MaterialTable
        title={
            <>
              <Button
                id="btnAdd"
                variant="contained"
                color="primary"
                href="/AddadminUser"
              >
                Add User
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
//       .get(${API_URL}/api/inspection, {
//         // headers: {
//         //   Authorization: Bearer ${localStorage.getItem("token")},
//         // },
//       })
//       .then((res) => {
//         setData(res.data);
//       });
//     retrieveInspectionDetails();
//   }, []);

//   const retrieveInspectionDetails = () => {
//     setinspectionDetails(null);
//     axios.get(${API_URL}/api/inspection, {}).then((res) => {
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

// export default Editable;