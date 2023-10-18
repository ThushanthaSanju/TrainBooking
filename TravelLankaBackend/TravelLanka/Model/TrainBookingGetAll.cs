using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace TravelLanka.Model
{
    public class TrainBookingGetAllResponse
    {
        public bool IsSuccess { get; set; }
        public string Message { get; set; }

        public List<TrainBookingInsertRequest> data { get; set; }
    }
}
