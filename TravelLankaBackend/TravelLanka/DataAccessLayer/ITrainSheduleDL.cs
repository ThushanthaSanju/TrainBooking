using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using TravelLanka.Model;

namespace TravelLanka.DataAccessLayer
{
  public  interface ITrainSheduleDL
    {
        public Task<TrainSheduleInsertResponse> TrainSheduleInsert(TrainSheduleInsertRequest request);
        public Task<TrainSheduleGetAllResponse> TrainSheduleGetAll();
        public Task<TrainSheduleGetByIDResponse> TrainSheduleGetByID(string ID);

        public Task<TrainSheduleGetByNameResponse> TrainSheduleGetByName(string From,string To,string Date);
        
         public Task<TrainSheduleUpdateRecordByIDResponse> TrainSheduleUpdateBYID(TrainSheduleInsertRequest request);
        public Task<TrainSheduleDeleteByIdResponse> TrainShedulDeleteBYID(TrainSheduleDeleteByIdRequest request);
      
    }
}
