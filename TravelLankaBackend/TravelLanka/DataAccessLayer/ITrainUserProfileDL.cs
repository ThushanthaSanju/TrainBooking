using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using TravelLanka.Model;

namespace TravelLanka.DataAccessLayer
{
    public interface ITrainUserProfileDL
    {
        public Task<TrainUserProfileInsertResponse> TrainUserProfileInsert(TrainUserProfileInsertRequest request, bool isAdmin);
       public  Task<TrainUserProfileInsertRequest> FindUserByUsername(string username);

        public Task<TrainUserProfileGetAllResponse> TrainUserProfileGetAll();

        public Task<TrainUserProfileGetByIDResponse> TrainUserProfileGetByID(string ID);


        public Task<TrainUserProfileUpdateRecordByIDResponse> TrainUserProfileUpdateRecordByID(TrainUserProfileInsertRequest request);
        

        public Task<TrainUserProfileDeleteByIdResponse> TrainUserProfileDeleteByID(TrainUserProfileDeleteByIdRequest request);
    }
}
