using Microsoft.Extensions.Configuration;
using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using TravelLanka.Model;

namespace TravelLanka.DataAccessLayer
{
    public class TrainBookingDL : ITrainBookingDL
    {
        private readonly IConfiguration _configuration;
        private readonly MongoClient _mongoClient;
        private readonly IMongoCollection<TrainBookingInsertRequest> _mongoCollection;

        public TrainBookingDL(IConfiguration configuration)
        {
            _configuration = configuration;
            _mongoClient = new MongoClient(_configuration["DatabaseSettings:ConnectionString"]);
            var _MongoDatabase = _mongoClient.GetDatabase(_configuration["DatabaseSettings:DatabaseName"]);
            _mongoCollection = _MongoDatabase.GetCollection<TrainBookingInsertRequest>(_configuration["DatabaseSettings:CollectionNameB"]);
        }

        public async Task<TrainBookingInsertResponse> TrainBookingInsert(TrainBookingInsertRequest request)
        {
            TrainBookingInsertResponse response = new TrainBookingInsertResponse();
            response.IsSuccess = true;
            response.Message = "Data Successfullly Inserted";

            try
            {

               

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
        }



        public async Task<TrainBookingGetAllResponse> TrainBookingGetAll()
        {
            TrainBookingGetAllResponse response = new TrainBookingGetAllResponse();
            response.IsSuccess = true;
            response.Message = "Data Fetch Successfully";

            try
            {
                response.data = new List<TrainBookingInsertRequest>();
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


        public async Task<TrainBookingGetByIDResponse> TrainBookingGetByID(string ID)
        {
            TrainBookingGetByIDResponse response = new TrainBookingGetByIDResponse();
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

        public async Task<TrainBookingUpdateRecordByIDResponse> TrainBookingUpdateBYID(TrainBookingInsertRequest request)
        {
            TrainBookingUpdateRecordByIDResponse response = new TrainBookingUpdateRecordByIDResponse();
            response.IsSuccess = true;
            response.Message = "Update Record Successfully By Id";
            try
            {

                TrainBookingGetByIDResponse response1 = await TrainBookingGetByID(request.Id);
                request.CreatedDate = response1.data.name;
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


        public async Task<TrainBookingDeleteByIdResponse> TrainBookingDeleteBYID(TrainBookingDeleteByIdRequest request)
        {
            TrainBookingDeleteByIdResponse response = new TrainBookingDeleteByIdResponse();
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


    }
}
