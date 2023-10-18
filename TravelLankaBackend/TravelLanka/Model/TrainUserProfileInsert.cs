using MongoDB.Bson.Serialization.Attributes;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace TravelLanka.Model
{
    public class TrainUserProfileInsertRequest
    {
        [BsonId]
        [BsonRepresentation(MongoDB.Bson.BsonType.ObjectId)]
        public string? Id { get; set; }

        [Required]
        public string Title { get; set; }

        [Required]
        [BsonElement("name")]
        public string Name { get; set; }

        [Required]
        public string NIC { get; set; }
        [Required]
        public string MobileNumber { get; set; }

        [Required]
        public string UserName { get; set; }

        [Required]
        [MinLength(6)] // Adjust the minimum length as needed
        public string Password { get; set; }

        [Required]
        [Compare("Password")] // Ensure that ConfirmPassword matches Password
        public string ConfirmPassword { get; set; }

        public bool StatusOfActivation { get; set; }

        public string CreatedDate { get; set; }

        public string UpdateDate { get; set; }

        public bool adminoruser { get; set; }

    }
    public class TrainUserProfileInsertResponse
    {
        public bool IsSuccess { get; set; }

        public string Message { get; set; }
    }
    public class LoginRequest
    {
        public string UserName { get; set; }
        public string Password { get; set; }
    }

}