package ro.alexmamo.firebasesigninwithemailandpassword.core

object Constants {
    //App
    const val TAG = "AppTag"

    //Buttons
    const val SIGN_IN_BUTTON = "Sign in"
    const val RESET_PASSWORD_BUTTON = "Reset"
    const val SIGN_UP_BUTTON = "Sign up"

    //Menu Items
    const val SIGN_OUT_ITEM = "Sign out"
    const val DELETE_USER_ITEM = "Delete user"

    //Screens
    const val SIGN_IN_SCREEN = "Sign in"
    const val FORGOT_PASSWORD_SCREEN = "Forgot password"
    const val SIGN_UP_SCREEN = "Sign up"
    const val PROFILE_SCREEN = "Profile"

    //Labels
    const val EMAIL_LABEL = "Email"
    const val PASSWORD_LABEL = "Password"
    const val SIGN_OUT_ACTION_LABEL = "Sign out?"

    //Useful
    const val EMPTY_STRING = ""
    const val VERTICAL_DIVIDER = "|"

    //Texts
    const val FORGOT_PASSWORD = "Forgot password?"
    const val NO_ACCOUNT = "No account? Sign up."
    const val ALREADY_USER = "Already a user? Sign in."
    const val WELCOME_MESSAGE = "Welcome to our app."
    const val ALREADY_VERIFIED = "Already verified?"
    const val SPAM_EMAIL = "If not, please also check the spam folder."

    //Messages
    const val EMAIL_VERIFICATION_SENT_MESSAGE = "We've sent you an email with a link to verify the email."
    const val EMAIL_NOT_VERIFIED_MESSAGE = "Your email is not verified."
    const val RESET_PASSWORD_MESSAGE = "We've sent you an email with a link to reset the password."
    const val DELETE_USER_MESSAGE = "You need to re-authenticate before deleting the user."
    const val USER_DELETED_MESSAGE = "The user has been deleted."

    //Error Messages
    const val SENSITIVE_OPERATION_MESSAGE = "This operation is sensitive and requires recent authentication. Log in again before retrying this request."
}