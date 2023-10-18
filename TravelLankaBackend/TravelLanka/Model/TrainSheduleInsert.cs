using MongoDB.Bson.Serialization.Attributes;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace TravelLanka.Model
{
    public class TrainSheduleInsertRequest
    {
        [BsonId]
        [BsonRepresentation(MongoDB.Bson.BsonType.ObjectId)]
        public string? Id { get; set; }

        [Required]
        [BsonElement("name")]
        public string name { get; set; }

        
        public string arrivedTime { get; set; }

       
        public string departureTime { get; set; }

       
        public int priceForOneSeat { get; set; }

       
        public string from { get; set; }

       
        public string to { get; set; }

        
        public int numberOfSeat { get; set; }


        public bool status { get; set; }
        public bool publish { get; set; }

        public string date { get; set; }

        public string CreatedDate { get; set; }

        public string UpdateDate { get; set; }
    }

    public class TrainSheduleInsertResponse
    {
        public bool IsSuccess { get; set; }

        public string Message { get; set; }
    }
}
