using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace TravelLanka.Model
{
    public class TrainSheduleGetByIDResponse
    {
        public bool IsSuccess { get; set; }
        public string Message { get; set; }

        public TrainSheduleInsertRequest data { get; set; }
    } 

    public class TrainSheduleGetByNameResponse
    {
        public bool IsSuccess { get; set; }
        public string Message { get; set; }

        public List<TrainSheduleInsertRequest> data { get; set; }
    }
}
