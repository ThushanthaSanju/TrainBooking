using Microsoft.AspNetCore.Mvc;
using System;
using System.Threading.Tasks;
using TravelLanka.DataAccessLayer;
using TravelLanka.Model;

namespace TravelLanka.Controllers
{
    [Route("api/auth")]
    [ApiController]
    public class AuthController : ControllerBase
    {
        private readonly ITrainUserProfileDL _trainUserProfileDL;

        public AuthController(ITrainUserProfileDL trainUserProfileDL)
        {
            _trainUserProfileDL = trainUserProfileDL;
        }

        [HttpPost("login")]
        public async Task<IActionResult> Login(LoginRequest loginRequest)
        {
            try
            {
                // Find the user by username
                var user = await _trainUserProfileDL.FindUserByUsername(loginRequest.UserName);

                // Check if the user exists and if the provided password matches the stored password
                if (user != null && loginRequest.Password == user.Password)
                {
                    // Authentication successful
                    // You may generate a JWT token here if needed

                    return Ok("Authentication successful");
                }

                // Authentication failed
                return Unauthorized("Invalid username or password");
            }
            catch (Exception ex)
            {
                return StatusCode(500, $"Internal server error: {ex.Message}");
            }
        }
    }
}
