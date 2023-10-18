using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using TravelLanka.Model;

namespace TravelLanka.DataAccessLayer
{
  public  interface ITrainBookingDL
    {
        public Task<TrainBookingInsertResponse> TrainBookingInsert(TrainBookingInsertRequest request);

        public Task<TrainBookingGetAllResponse> TrainBookingGetAll();
        public Task<TrainBookingGetByIDResponse> TrainBookingGetByID(string ID);
        public Task<TrainBookingUpdateRecordByIDResponse> TrainBookingUpdateBYID(TrainBookingInsertRequest request);


        public Task<TrainBookingDeleteByIdResponse> TrainBookingDeleteBYID(TrainBookingDeleteByIdRequest request);
    }
}
