package com.blurryface.analyzer.exception;

public class ExceptionsDetails {

    public static final String DEFAULT_MESSAGE = "Something went wrong! Unable to process the request at this moment. Please try again later.";
    public static final String INVALID_REQUEST = "Invalid Request!";

    public static final String EXPIRED_VERIFICATION_LINK = "The verification link has expired! Please request a new one.";
    public static final String INVALID_VERIFICATION_LINK = "The verification link is invalid! Please request a new one.";
    public static final String EXPIRED_OTP = "The OTP has expired! Please request a new one.";
    public static final String INCORRECT_OTP = "Incorrect OTP!";
    public static final String UNSUCCESSFUL_OTP_ATTEMPT = "Too many unsuccessful attempts! Please resend OTP or try again.";
    public static final String OTP_REQUEST_LIMIT = "You've reached the maximum limit of OTP requests!";
    public static final String INVALID_COMMUNICATION_CHANNEL = "Communication Channel unavailable for this request! Please use other channels.";

    public static final String INVALID_VERIFICATION_TOKEN = "[INVALID REQUEST] :: Invalid Token!";

    public static final String DECRYPTION_ERROR = "[INVALID REQUEST] :: Encryption Error! Unable to decrypt data.";

    public static final String USERNAME_POLICY_ERROR = "The username doesn't meet our guidelines! Please check the username policy.";
    public static final String PASSWORD_POLICY_ERROR = "The password does not meet our security guidelines! Please check the password policy.";
    public static final String SHOPIFY_SHOP_NAME_POLICY_ERROR = "The shop name is invalid! Please try again later.";

    public static final String USERNAME_TAKEN = "The username is already taken! Please continue with a different username";

    public static final String INVALID_CREDENTIALS = "Invalid credentials! Please enter a valid email/mobile/username.";
    public static final String CREDENTIALS_NOT_FOUND = "Invalid credentials! Please confirm if you've signed up on our platform or if the password is correct.";
    public static final String INVALID_EMAIL = "The email address is invalid!";
    public static final String INVALID_MOBILE = "The mobile is invalid!";
    public static final String INVALID_USERNAME = "The username is invalid!";

    public static final String INVALID_GRANT_TYPE = "[INVALID REQUEST] :: Invalid Grant Type! Unable to acquire session.";
    public static final String INVALID_REFRESH_TOKEN = "[INVALID REQUEST] :: Invalid Refresh Token! Unable to acquire session.";
    public static final String EXPIRED_REFRESH_TOKEN = "The refresh token has expired! Please authenticate again.";
    public static final String COMPROMISED_REFRESH_TOKEN = "[ALERT] :: We suspect that your account might have been compromised! It's highly recommended that you log-out from all devices.";
    public static final String DEVICE_ID_MISSING = "[INVALID REQUEST] :: X-Device-Id missing! Unable to acquire session.";
    public static final String SR_TOKEN_MISSING = "[INVALID REQUEST] :: X-Sr-Token is missing! Unable to process request.";
    public static final String SAME_DEVICE_LOGIN_REQUEST = "[INVALID REQUEST] :: A session is already active on this device.";
    public static final String INVALID_SESSION = "[INVALID REQUEST] :: Invalid Session! Please request a new session.";

    public static final String PASSWORD_REQUIRED_ERROR = "[INVALID REQUEST] :: Password is required!";
    public static final String PASSWORD_NOT_SET = "[INVALID REQUEST] :: You haven't set a password with us! Please reset the password to continue or log-in via other methods (OTP/Social)";
    public static final String PASSWORD_RESET_ERROR = "Unable to reset password! Please try again later.";
    public static final String MULTIPLE_ACCOUNTS_EXCEPTION = "Multiple accounts found with this mobile! Please use your email or username.";
    public static final String PASSWORD_REUSE_ERROR = "Users aren't allowed to reuse last 3 passwords! Please retry with another password.";

    public static final String SERVICE_NOT_REGISTERED = "[INVALID REQUEST] :: Service not registered with Authmaster! Unable to process request.";
    public static final String X_REFERER_MISSING = "[INVALID REQUEST] :: X-Referer missing! Unable to process request.";
    public static final String INVALID_SECRET = "[INVALID REQUEST] :: Invalid/Missing X-Service-Secret! Unable to process request.";
    public static final String USER_DETAILS_NOT_FOUND = "[INVALID REQUEST] :: User Details Not Found!";
    public static final String TENANT_ID_MISSING = "[INVALID REQUEST] :: X-Tenant-Id missing! Unable to process request.";
    public static final String INVALID_OAUTH_TOKEN = "[INVALID REQUEST] :: Invalid OAuth Token! Unable to process request.";
    public static final String INVALID_TOKEN = "[INVALID REQUEST] :: Invalid Token! Unable to process request.";
    public static final String UNAUTHORIZED_APP_SWITCH = "[INVALID REQUEST] :: You're not authorized to perform the app switch! Please ask your admin to invite you on the platform.";


    public static class INTERNAL_ERROR {
        public static String CODE = "AUT251";
        public static String TITLE = "Internal Server Error";
        public static String MESSAGE = "Something went wrong! Please try again later.";
    }

    public static class CUSTOM_EXCEPTION {
        public static String CODE = "AUT252";
        public static String TITLE = "Custom Exception";
        public static String MESSAGE = "";
    }

    public static class CONSTRAINTS_VIOLATION {
        public static String CODE = "AUT253";
        public static String TITLE = "Constraints Violated";
        public static String MESSAGE = "[INVALID REQUEST] :: Constraints violated for the request!";
    }

    public static class INVALID_METHOD_ARGUMENTS {
        public static String CODE = "AUT254";
        public static String TITLE = "Invalid Method Arguments";
        public static String MESSAGE = "[INVALID REQUEST] :: Method arguments not valid!";
    }

    public static class DUPLICATE_USER {
        public static String CODE = "AUT256";
        public static String TITLE = "Duplicate User";
        public static String MESSAGE = "";
    }

    public static class INVALID_AUTHENTICATION_STRATEGY {
        public static String CODE = "AUT257";
        public static String TITLE = "Invalid Authentication Strategy";
        public static String MESSAGE = "";
    }
}
