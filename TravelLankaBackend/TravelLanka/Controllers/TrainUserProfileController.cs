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
    public class TrainUserProfileController : ControllerBase
    {
        private readonly ITrainUserProfileDL _trainUserProfileDL;
    
        public TrainUserProfileController(ITrainUserProfileDL trainUserProfileDL)
        {
            _trainUserProfileDL = trainUserProfileDL;
        }
       /* [HttpPost]
         public async Task<IActionResult> TrainUserProfileInsert(TrainUserProfileInsertRequest request)
          {
              TrainUserProfileInsertResponse response = new TrainUserProfileInsertResponse();

              try
              {
                  var existingUser = await _trainUserProfileDL.FindUserByUsername(request.NIC);
                  if (existingUser != null)
                  {
                      return BadRequest("User with username already exixtes");

                  }
                  response = await _trainUserProfileDL.TrainUserProfileInsert(request);

              }
              catch (Exception ex)
              {
                  response.IsSuccess = false;
                  response.Message = "Excepation Occures:" + ex.Message;
              }
              return Ok(response);

          }*/

        [HttpPost]
        public async Task<IActionResult> Register(TrainUserProfileInsertRequest registrationRequest, bool isAdmin)
        {
            try
            {
                // Call the modified TrainUserProfileInsert method with the isAdmin flag
                var response = await _trainUserProfileDL.TrainUserProfileInsert(registrationRequest, isAdmin);

                if (response.IsSuccess)
                {
                    return Ok("Registration successful");
                }
                else
                {
                    return BadRequest(response.Message);
                }
            }
            catch (Exception ex)
            {
                return StatusCode(500, $"Internal server error: {ex.Message}");
            }
        }



        [HttpGet]
        public async Task<IActionResult> TrainUserProfileGetAll()
        {
            TrainUserProfileGetAllResponse response = new TrainUserProfileGetAllResponse();

            try
            {

                response = await _trainUserProfileDL.TrainUserProfileGetAll();

            }
            catch (Exception ex)
            {
                response.IsSuccess = false;
                response.Message = "Excepation Occures:" + ex.Message;
            }
            return Ok(response);

        }

        [HttpGet]
        public async Task<IActionResult> TrainUserProfileGetByID([FromQuery] string ID)
        {
            TrainUserProfileGetByIDResponse response = new TrainUserProfileGetByIDResponse();

            try
            {

                response = await _trainUserProfileDL.TrainUserProfileGetByID(ID);

            }
            catch (Exception ex)
            {
                response.IsSuccess = false;
                response.Message = "Excepation Occures:" + ex.Message;
            }
            return Ok(response);

        }





        [HttpPut]
        public async Task<IActionResult> TrainUserProfileUpdateRecordByID(TrainUserProfileInsertRequest request)
        {
            TrainUserProfileUpdateRecordByIDResponse response = new TrainUserProfileUpdateRecordByIDResponse();

            try
            {

                response = await _trainUserProfileDL.TrainUserProfileUpdateRecordByID(request);

            }
            catch (Exception ex)
            {
                response.IsSuccess = false;
                response.Message = "Excepation Occures:" + ex.Message;
            }
            return Ok(response);

        }


        [HttpDelete]
        public async Task<IActionResult> TrainUserProfileDeleteByID(TrainUserProfileDeleteByIdRequest request)
        {
            TrainUserProfileDeleteByIdResponse response = new TrainUserProfileDeleteByIdResponse();

            try
            {
                response = await _trainUserProfileDL.TrainUserProfileDeleteByID(request);
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
