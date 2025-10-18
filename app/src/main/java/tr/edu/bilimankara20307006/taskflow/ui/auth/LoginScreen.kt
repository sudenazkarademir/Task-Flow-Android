package tr.edu.bilimankara20307006.taskflow.ui.auth

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    authViewModel: AuthViewModel = viewModel(),
    onNavigateToMain: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    
    val authState by authViewModel.authState.collectAsState()
    
    LaunchedEffect(authState.isAuthenticated) {
        if (authState.isAuthenticated) {
            onNavigateToMain()
        }
    }
    
    val darkBackground = Color(0xFF1C1C1E)
    val greenColor = Color(0xFF4CAF50)
    val inputBackground = Color(0xFF2C2C2E)
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(greenColor.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PushPin,
                    contentDescription = "Raptiye",
                    tint = greenColor,
                    modifier = Modifier
                        .size(60.dp)
                        .rotate(45f)
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                text = "Raptiye",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Hoş Geldiniz",
                fontSize = 18.sp,
                color = Color.White.copy(alpha = 0.6f)
            )
            
            Spacer(modifier = Modifier.height(64.dp))
            
            Text(
                text = "Hoş Geldiniz",
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Giriş Yap",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.6f),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { 
                    Text(
                        "E-posta", 
                        color = Color.White.copy(alpha = 0.4f)
                    ) 
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = "Email",
                        tint = Color.White.copy(alpha = 0.4f)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = greenColor,
                    unfocusedContainerColor = inputBackground,
                    focusedContainerColor = inputBackground,
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White,
                    cursorColor = greenColor
                ),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { 
                    Text(
                        "Şifre", 
                        color = Color.White.copy(alpha = 0.4f)
                    ) 
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = "Şifre",
                        tint = Color.White.copy(alpha = 0.4f)
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (passwordVisible) "Şifreyi gizle" else "Şifreyi göster",
                            tint = Color.White.copy(alpha = 0.4f)
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = greenColor,
                    unfocusedContainerColor = inputBackground,
                    focusedContainerColor = inputBackground,
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White,
                    cursorColor = greenColor
                ),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "Şifremi unuttum",
                fontSize = 14.sp,
                color = greenColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { },
                textAlign = TextAlign.End
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = {
                    authViewModel.signIn(email, password)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = greenColor,
                    contentColor = Color.White
                ),
                enabled = email.isNotEmpty() && password.isNotEmpty()
            ) {
                Text(
                    text = "Giriş Yap",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Hesabın yok mu? ",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.6f)
                )
                Text(
                    text = "Kayıt Ol",
                    fontSize = 14.sp,
                    color = greenColor,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable {
                        authViewModel.signUp(email, password)
                    }
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
            
            if (authState.isLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator(
                    color = greenColor,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}