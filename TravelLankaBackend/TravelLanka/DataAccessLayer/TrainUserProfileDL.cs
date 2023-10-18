using Microsoft.Extensions.Configuration;
using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using TravelLanka.Model;

namespace TravelLanka.DataAccessLayer
{
    public class TrainUserProfileDL: ITrainUserProfileDL
    {
        private readonly IConfiguration _configuration;
        private readonly MongoClient _mongoClient;
        private readonly IMongoCollection<TrainUserProfileInsertRequest> _mongoCollection;


        public TrainUserProfileDL(IConfiguration configuration)
        {
            _configuration = configuration;
            _mongoClient = new MongoClient(_configuration["DatabaseSettings:ConnectionString"]);
            var _MongoDatabase = _mongoClient.GetDatabase(_configuration["DatabaseSettings:DatabaseName"]);
            _mongoCollection = _MongoDatabase.GetCollection<TrainUserProfileInsertRequest>(_configuration["DatabaseSettings:CollectionNameUser"]);
        }

      /*  public async Task<TrainUserProfileInsertResponse> TrainUserProfileInsert(TrainUserProfileInsertRequest request)
        {
            TrainUserProfileInsertResponse response = new TrainUserProfileInsertResponse();
            response.IsSuccess = true;
            response.Message = "Data Successfullly Inserted";

            try
            {

                *//* request.ArrivedTime = string.Empty;

                 request.DepartureTime = string.Empty;*//*

                request.CreatedDate = DateTime.Now.ToString();
                request.UpdateDate = string.Empty;

                await _mongoCollection.InsertOneAsync(request);


            }
            catch (Exception ex)
            {
                response.IsSuccess = false;
                response.Message = "Excepation Occures:" + ex.Message;
            }
            return response;
        }*/

        // Inside TrainUserProfileDL class
        public async Task<TrainUserProfileInsertRequest> FindUserByUsername(string username)
        {
            var filter = Builders<TrainUserProfileInsertRequest>.Filter.Eq(x => x.UserName, username);
            return await _mongoCollection.Find(filter).FirstOrDefaultAsync();
        }


        public async Task<TrainUserProfileGetAllResponse> TrainUserProfileGetAll()
        {
            TrainUserProfileGetAllResponse response = new TrainUserProfileGetAllResponse();
            response.IsSuccess = true;
            response.Message = "Data Fetch Successfully";

            try
            {
                response.data = new List<TrainUserProfileInsertRequest>();
                response.data = await _mongoCollection.Find(x => true).ToListAsync();
                if (response.data.Count == 0)
                {
                    response.Message = "No Record Found";

                }
            }
            catch (Exception ex)
            {
                response.IsSuccess = false;
                response.Message = "Excepation Occures:" + ex.Message;
            }
            return response;
        }

        public async Task<TrainUserProfileGetByIDResponse> TrainUserProfileGetByID(string ID)
        {
            TrainUserProfileGetByIDResponse response = new TrainUserProfileGetByIDResponse();
            response.IsSuccess = true;
            response.Message = "Fetch Data Successfullly BY ID";
            try
            {

                response.data = await _mongoCollection.Find(x => (x.Id == ID)).FirstOrDefaultAsync();
                if (response.data == null)
                {
                    response.Message = "Invalid Id Please Enter Valid ID";
                }
            }
            catch (Exception ex)
            {
                response.IsSuccess = false;
                response.Message = "Excepation Occures:" + ex.Message;
            }
            return response;



        }






        public async Task<TrainUserProfileUpdateRecordByIDResponse> TrainUserProfileUpdateRecordByID(TrainUserProfileInsertRequest request)
        {
            TrainUserProfileUpdateRecordByIDResponse response = new TrainUserProfileUpdateRecordByIDResponse();
            response.IsSuccess = true;
            response.Message = "Update Record Successfully By Id";
            try
            {

                TrainUserProfileGetByIDResponse response1 = await TrainUserProfileGetByID(request.Id);
                request.CreatedDate = response1.data.Name;
                request.UpdateDate = DateTime.Now.ToString();

                var Result = await _mongoCollection.ReplaceOneAsync(x => x.Id == request.Id, request);

                if (!Result.IsAcknowledged)
                {
                    response.Message = "Input Id Not Found / Updating Not Occurss";
                }

            }
            catch (Exception ex)
            {
                response.IsSuccess = false;
                response.Message = "Excepation Occures:" + ex.Message;
            }
            return response;
        }



        public async Task<TrainUserProfileDeleteByIdResponse> TrainUserProfileDeleteByID(TrainUserProfileDeleteByIdRequest request)
        {
            TrainUserProfileDeleteByIdResponse response = new TrainUserProfileDeleteByIdResponse();
            response.IsSuccess = true;
            response.Message = "Delete Record Successfully By Id";
            try
            {

                var Result = await _mongoCollection.DeleteOneAsync(x => x.Id == request.ID);
                if (!Result.IsAcknowledged)
                {
                    response.Message = "Input Id Not Found / Delete Not Occurss";
                }

            }
            catch (Exception ex)
            {
                response.IsSuccess = false;
                response.Message = "Excepation Occures:" + ex.Message;
            }
            return response;

        }

        public async Task<TrainUserProfileInsertResponse> TrainUserProfileInsert(TrainUserProfileInsertRequest request, bool isAdmin)
        {
            TrainUserProfileInsertResponse response = new TrainUserProfileInsertResponse();
            response.IsSuccess = true;
            response.Message = "Data Successfully Inserted";

            try
            {
                // Set the user type (admin or customer)
                request.adminoruser = isAdmin;

                request.CreatedDate = DateTime.Now.ToString();
                request.UpdateDate = string.Empty;

                await _mongoCollection.InsertOneAsync(request);
            }
            catch (Exception ex)
            {
                response.IsSuccess = false;
                response.Message = "Exception Occurred: " + ex.Message;
            }
            return response;
        }

    }
}
