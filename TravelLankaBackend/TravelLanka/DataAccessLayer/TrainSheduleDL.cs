using Microsoft.Extensions.Configuration;
using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using TravelLanka.Model;

namespace TravelLanka.DataAccessLayer
{
    public class TrainSheduleDL: ITrainSheduleDL
    {
        private readonly IConfiguration _configuration;
        private readonly MongoClient _mongoClient;
        private readonly IMongoCollection<TrainSheduleInsertRequest> _mongoCollection;

        public TrainSheduleDL(IConfiguration configuration)
        {
            _configuration = configuration;
            _mongoClient = new MongoClient(_configuration["DatabaseSettings:ConnectionString"]);
            var _MongoDatabase = _mongoClient.GetDatabase(_configuration["DatabaseSettings:DatabaseName"]);
            _mongoCollection = _MongoDatabase.GetCollection< TrainSheduleInsertRequest>(_configuration["DatabaseSettings:CollectionName"]);
        }

        

        public async Task<TrainSheduleInsertResponse> TrainSheduleInsert(TrainSheduleInsertRequest request)
        {
            TrainSheduleInsertResponse response = new TrainSheduleInsertResponse();
            response.IsSuccess = true;
            response.Message = "Data Successfullly Inserted";

            try
            {

                /* request.ArrivedTime = string.Empty;

                 request.DepartureTime = string.Empty;*/

                request.CreatedDate = DateTime.Now.ToString();
                request.UpdateDate = string.Empty;

                await _mongoCollection.InsertOneAsync(request);


            }
            catch(Exception ex)
            {
                response.IsSuccess = false;
                response.Message = "Excepation Occures:" + ex.Message;
            }
            return response;
        }

        public async Task<TrainSheduleGetAllResponse> TrainSheduleGetAll()
        {
            TrainSheduleGetAllResponse response = new TrainSheduleGetAllResponse();
            response.IsSuccess = true;
            response.Message = "Data Fetch Successfully";

            try
            {
                response.data = new List<TrainSheduleInsertRequest>();
                response.data = await _mongoCollection.Find(x => true).ToListAsync();
                if(response.data.Count==0)
                {
                    response.Message = "No Record Found";

                }
            }
            catch(Exception ex)
            {
                response.IsSuccess = false;
                response.Message = "Excepation Occures:" + ex.Message;
            }
            return response;
        }

        public async Task<TrainSheduleGetByIDResponse> TrainSheduleGetByID(string ID)
        {
            TrainSheduleGetByIDResponse response = new TrainSheduleGetByIDResponse();
            response.IsSuccess = true;
            response.Message = "Fetch Data Successfullly BY ID";
            try
            {

                response.data = await _mongoCollection.Find(x => (x.Id == ID)).FirstOrDefaultAsync();
                if(response.data == null)
                {
                    response.Message = "Invalid Id Please Enter Valid ID";
                }
            }
            catch(Exception ex)
            {
                response.IsSuccess = false;
                response.Message = "Excepation Occures:" + ex.Message;
            }
            return response;

        

        }

        public async Task<TrainSheduleGetByNameResponse> TrainSheduleGetByName(string From,string To,string Date)
        {
            TrainSheduleGetByNameResponse response = new TrainSheduleGetByNameResponse();
            response.IsSuccess = true;
            response.Message = "Fetch Data Successfullly BY Name";
            try
            {
                response.data = new List<TrainSheduleInsertRequest>();
                response.data = await _mongoCollection.Find(x => (x.from == From)&&(x.to==To) && (x.date == Date)).ToListAsync();
                if (response.data.Count ==0)
                {
                    response.Message = "Invalid Id Please Enter Valid Name";
                }
            }
            catch (Exception ex)
            {
                response.IsSuccess = false;
                response.Message = "Excepation Occures:" + ex.Message;
            }
            return response;
        }

        public async Task<TrainSheduleUpdateRecordByIDResponse> TrainSheduleUpdateBYID(TrainSheduleInsertRequest request)
        {
            TrainSheduleUpdateRecordByIDResponse response = new TrainSheduleUpdateRecordByIDResponse();
            response.IsSuccess = true;
            response.Message = "Update Record Successfully By Id";
            try
            {

                TrainSheduleGetByIDResponse response1 = await TrainSheduleGetByID(request.Id);
                request.CreatedDate = response1.data.name;
                request.UpdateDate = DateTime.Now.ToString();

                 var Result =  await _mongoCollection.ReplaceOneAsync(x => x.Id == request.Id, request);

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

        public async Task<TrainSheduleDeleteByIdResponse> TrainShedulDeleteBYID(TrainSheduleDeleteByIdRequest request)
        {
            TrainSheduleDeleteByIdResponse response = new TrainSheduleDeleteByIdResponse();
            response.IsSuccess = true;
            response.Message = "Delete Record Successfully By Id";
            try
            {

                var Result = await _mongoCollection.DeleteOneAsync(x => x.Id == request.ID);
                if (!Result.IsAcknowledged)
                {
                    response.Message = "Input Id Not Found / Delete Not Occurss";
                }

            } catch(Exception ex)
            {
                response.IsSuccess = false;
                response.Message = "Excepation Occures:" + ex.Message;
            }
            return response;

        }



       

      
    }
}
