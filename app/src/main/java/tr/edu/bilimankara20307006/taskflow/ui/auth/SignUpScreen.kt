package tr.edu.bilimankara20307006.taskflow.ui.auth

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import tr.edu.bilimankara20307006.taskflow.ui.localization.LocalizationManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    authViewModel: AuthViewModel = viewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToMain: () -> Unit
) {
    val context = LocalContext.current
    val localizationManager = remember { LocalizationManager.getInstance(context) }
    
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    
    val authState by authViewModel.authState.collectAsState()
    
    // Animasyon state'leri
    var logoVisible by remember { mutableStateOf(false) }
    var logoInPosition by remember { mutableStateOf(false) }
    var titleVisible by remember { mutableStateOf(false) }
    var formVisible by remember { mutableStateOf(false) }
    var buttonVisible by remember { mutableStateOf(false) }
    
    // Tema renklerini MaterialTheme'den al
    val darkBackground = MaterialTheme.colorScheme.background
    val inputBackground = MaterialTheme.colorScheme.surfaceVariant
    val textColor = MaterialTheme.colorScheme.onBackground
    val textSecondaryColor = MaterialTheme.colorScheme.onSurfaceVariant
    val greenColor = Color(0xFF4CAF50)
    
    // Animasyon değerleri
    val logoScale by androidx.compose.animation.core.animateFloatAsState(
        targetValue = if (logoVisible) 1f else 0.3f,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "logoScale"
    )
    
    val logoAlpha by androidx.compose.animation.core.animateFloatAsState(
        targetValue = if (logoVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "logoAlpha"
    )
    
    val logoOffsetY by androidx.compose.animation.core.animateDpAsState(
        targetValue = if (logoInPosition) 0.dp else (-100).dp,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "logoOffsetY"
    )
    
    val titleAlpha by androidx.compose.animation.core.animateFloatAsState(
        targetValue = if (titleVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
        label = "titleAlpha"
    )
    
    val formOffsetY by androidx.compose.animation.core.animateDpAsState(
        targetValue = if (formVisible) 0.dp else 100.dp,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "formOffsetY"
    )
    
    val formAlpha by androidx.compose.animation.core.animateFloatAsState(
        targetValue = if (formVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "formAlpha"
    )
    
    val buttonOffsetY by androidx.compose.animation.core.animateDpAsState(
        targetValue = if (buttonVisible) 0.dp else 100.dp,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "buttonOffsetY"
    )
    
    val buttonAlpha by androidx.compose.animation.core.animateFloatAsState(
        targetValue = if (buttonVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "buttonAlpha"
    )
    
    LaunchedEffect(Unit) {
        delay(100)
        logoVisible = true
        delay(300)
        logoInPosition = true
        delay(300)
        titleVisible = true
        delay(200)
        formVisible = true
        delay(200)
        buttonVisible = true
    }
    
    LaunchedEffect(authState.user) {
        if (authState.user != null) {
            onNavigateToMain()
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
    ) {
        // Close button
        IconButton(
            onClick = onNavigateBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Geri",
                tint = textColor,
                modifier = Modifier.size(28.dp)
            )
        }
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 32.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .offset(y = logoOffsetY)
                    .scale(logoScale)
                    .alpha(logoAlpha)
                    .background(greenColor.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PushPin,
                    contentDescription = "Logo",
                    tint = greenColor,
                    modifier = Modifier
                        .size(70.dp)
                        .rotate(-45f)
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Başlık - Fade in
            Text(
                text = "Raptiye",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = greenColor,
                modifier = Modifier.alpha(titleAlpha)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = localizationManager.localizedString("CreateAccount"),
                fontSize = 18.sp,
                color = textSecondaryColor,
                modifier = Modifier.alpha(titleAlpha)
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // Form - Slide up
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = formOffsetY)
                    .alpha(formAlpha),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Email field
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { 
                        Text(
                            localizationManager.localizedString("Email"), 
                            color = textSecondaryColor
                        ) 
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Email,
                            contentDescription = localizationManager.localizedString("Email"),
                            tint = textSecondaryColor
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = greenColor,
                        unfocusedContainerColor = inputBackground,
                        focusedContainerColor = inputBackground,
                        unfocusedTextColor = textColor,
                        focusedTextColor = textColor,
                        cursorColor = greenColor
                    )
                )
                
                // Password field
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { 
                        Text(
                            localizationManager.localizedString("Password"), 
                            color = textSecondaryColor
                        ) 
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Lock,
                            contentDescription = localizationManager.localizedString("Password"),
                            tint = textSecondaryColor
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (passwordVisible) "Şifreyi gizle" else "Şifreyi göster",
                                tint = textSecondaryColor
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = greenColor,
                        unfocusedContainerColor = inputBackground,
                        focusedContainerColor = inputBackground,
                        unfocusedTextColor = textColor,
                        focusedTextColor = textColor,
                        cursorColor = greenColor
                    ),
                    singleLine = true
                )
                
                // Confirm Password field
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    placeholder = { 
                        Text(
                            localizationManager.localizedString("ConfirmPassword"), 
                            color = textSecondaryColor
                        ) 
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Lock,
                            contentDescription = localizationManager.localizedString("ConfirmPassword"),
                            tint = textSecondaryColor
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                            Icon(
                                imageVector = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (confirmPasswordVisible) "Şifreyi gizle" else "Şifreyi göster",
                                tint = textSecondaryColor
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = greenColor,
                        unfocusedContainerColor = inputBackground,
                        focusedContainerColor = inputBackground,
                        unfocusedTextColor = textColor,
                        focusedTextColor = textColor,
                        cursorColor = greenColor
                    ),
                    singleLine = true
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Sign Up Button - Slide up
            Button(
                onClick = {
                    if (password == confirmPassword && email.isNotEmpty() && password.isNotEmpty()) {
                        authViewModel.signUp(email, password)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .offset(y = buttonOffsetY)
                    .alpha(buttonAlpha),
                colors = ButtonDefaults.buttonColors(
                    containerColor = greenColor,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                enabled = email.isNotEmpty() && password.isNotEmpty() && password == confirmPassword
            ) {
                if (authState.isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(
                        text = localizationManager.localizedString("SignUp"),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Sign In Link
            Row(
                modifier = Modifier
                    .offset(y = buttonOffsetY)
                    .alpha(buttonAlpha),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = localizationManager.localizedString("AlreadyHaveAccount"),
                    fontSize = 14.sp,
                    color = textSecondaryColor
                )
                Text(
                    text = localizationManager.localizedString("SignIn"),
                    fontSize = 14.sp,
                    color = greenColor,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable {
                        onNavigateBack()
                    }
                )
            }
            
            if (password.isNotEmpty() && confirmPassword.isNotEmpty() && password != confirmPassword) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = localizationManager.localizedString("PasswordsDoNotMatch"),
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            
            if (authState.errorMessage != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = authState.errorMessage ?: "",
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
