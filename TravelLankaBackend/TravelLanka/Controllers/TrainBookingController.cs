using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using TravelLanka.DataAccessLayer;
using TravelLanka.Model;

namespace TravelLanka.Controllers
{
    [Route("api/[controller]/[Action]")]
    [ApiController]
    public class TrainBookingController : ControllerBase
    {
        private readonly ITrainBookingDL _trainBookingDL;
    
        public TrainBookingController(ITrainBookingDL trainBookingDL)
        {
            _trainBookingDL = trainBookingDL;
        }
        [HttpPost]
        public async Task<IActionResult> TrainBookingInsert(TrainBookingInsertRequest request)
        {
            TrainBookingInsertResponse response = new TrainBookingInsertResponse();

            try
            {

                response = await _trainBookingDL.TrainBookingInsert(request);

            }
            catch (Exception ex)
            {
                response.IsSuccess = false;
                response.Message = "Excepation Occures:" + ex.Message;
            }
            return Ok(response);

        }


        [HttpGet]
        public async Task<IActionResult> TrainBookingGetAll()
        {
            TrainBookingGetAllResponse response = new TrainBookingGetAllResponse();

            try
            {

                response = await _trainBookingDL.TrainBookingGetAll();

            }
            catch (Exception ex)
            {
                response.IsSuccess = false;
                response.Message = "Excepation Occures:" + ex.Message;
            }
            return Ok(response.data);

        }

        [HttpGet]
        public async Task<IActionResult> TrainBookingGetByID([FromQuery] string ID)
        {
            TrainBookingGetByIDResponse response = new TrainBookingGetByIDResponse();

            try
            {

                response = await _trainBookingDL.TrainBookingGetByID(ID);

            }
            catch (Exception ex)
            {
                response.IsSuccess = false;
                response.Message = "Excepation Occures:" + ex.Message;
            }
            return Ok(response.data);

        }





        [HttpPut]
        public async Task<IActionResult> TrainBookingUpdateBYID(TrainBookingInsertRequest request)
        {
            TrainBookingUpdateRecordByIDResponse response = new TrainBookingUpdateRecordByIDResponse();

            try
            {

                response = await _trainBookingDL.TrainBookingUpdateBYID(request);

            }
            catch (Exception ex)
            {
                response.IsSuccess = false;
                response.Message = "Excepation Occures:" + ex.Message;
            }
            return Ok(response);

        }


        [HttpDelete]
        public async Task<IActionResult> TrainBookingDeleteBYID(TrainBookingDeleteByIdRequest request)
        {
            TrainBookingDeleteByIdResponse response = new TrainBookingDeleteByIdResponse();

            try
            {
                response = await _trainBookingDL.TrainBookingDeleteBYID(request);
            }
            catch (Exception ex)
            {
                response.IsSuccess = false;
                response.Message = "Excepation Occures:" + ex.Message;
            }
            return Ok(response);
        }
    }


}
