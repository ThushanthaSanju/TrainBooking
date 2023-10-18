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
    public class TrainSheduleController : ControllerBase
    {
        private readonly ITrainSheduleDL _trainSheduleDL;
    
        public TrainSheduleController(ITrainSheduleDL trainSheduleDL)
        {
            _trainSheduleDL = trainSheduleDL;
        }
        [HttpPost]
        public async Task<IActionResult> TrainSheduleInsert(TrainSheduleInsertRequest request)
        {
            TrainSheduleInsertResponse response = new TrainSheduleInsertResponse();

            try
            {

                response = await _trainSheduleDL.TrainSheduleInsert(request);

            }catch(Exception ex)
            {
                response.IsSuccess = false;
                response.Message = "Excepation Occures:" + ex.Message;
            }
            return Ok(response);

        }
        [HttpGet]
        public async Task<IActionResult> TrainSheduleGetAll()
        {
            TrainSheduleGetAllResponse response = new TrainSheduleGetAllResponse();

            try
            {

                response = await _trainSheduleDL.TrainSheduleGetAll();

            }
            catch (Exception ex)
            {
                response.IsSuccess = false;
                response.Message = "Excepation Occures:" + ex.Message;
            }
            return Ok(response.data);

        }


        [HttpGet]
        public async Task<IActionResult> TrainSheduleGetByID([FromQuery]string ID)
        {
            TrainSheduleGetByIDResponse response = new TrainSheduleGetByIDResponse();

            try
            {

                response = await _trainSheduleDL.TrainSheduleGetByID(ID);

            }
            catch (Exception ex)
            {
                response.IsSuccess = false;
                response.Message = "Excepation Occures:" + ex.Message;
            }
            return Ok(response.data);

        }


        [HttpGet]
        public async Task<IActionResult> TrainSheduleGetByName([FromQuery] string From,string To,string Date)
        {
            TrainSheduleGetByNameResponse response = new TrainSheduleGetByNameResponse();

            try
            {

                response = await _trainSheduleDL.TrainSheduleGetByName(From,To,Date);

            }
            catch (Exception ex)
            {
                response.IsSuccess = false;
                response.Message = "Excepation Occures:" + ex.Message;
            }
            return Ok(response.data);

        }


        [HttpPut]
        public async Task<IActionResult> TrainSheduleUpdateBYID(TrainSheduleInsertRequest request)
        {
            TrainSheduleUpdateRecordByIDResponse response = new TrainSheduleUpdateRecordByIDResponse();

            try
            {

                response = await _trainSheduleDL.TrainSheduleUpdateBYID(request);

            }
            catch (Exception ex)
            {
                response.IsSuccess = false;
                response.Message = "Excepation Occures:" + ex.Message;
            }
            return Ok(response);

        }


        [HttpDelete]
        public async Task<IActionResult> TrainShedulDeleteBYID(TrainSheduleDeleteByIdRequest request)
        {
            TrainSheduleDeleteByIdResponse response = new TrainSheduleDeleteByIdResponse();

            try
            {
                response = await _trainSheduleDL.TrainShedulDeleteBYID(request);
            }
            catch(Exception ex)
            {
                response.IsSuccess = false;
                response.Message = "Excepation Occures:" + ex.Message;
            }
            return Ok(response);
        }



    }


}
