package com.example.handyhub.ui.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.handyhub.ui.theme.HandyHubTheme
import com.example.handyhub.ui.theme.MaroonPrimary
import com.example.handyhub.ui.theme.OffWhiteBackground
import com.example.handyhub.ui.theme.TextDark

enum class AuthMode {
    LOG_IN,
    REGISTER
}

@Composable
fun AuthScreen(
    role: Role = Role.EMPLOYEE,
    initialMode: AuthMode = AuthMode.LOG_IN,
    onBackClick: () -> Unit = {},
    onSubmit: (AuthMode, Role, String, String, String) -> Unit = { _, _, _, _, _ -> },
    onGoogleSignIn: () -> Unit = {}
) {
    var mode by remember { mutableStateOf(initialMode) }

    // Form fields
    var fullName by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhiteBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top Red Header Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaroonPrimary)
                    .padding(horizontal = 24.dp)
                    .padding(top = 44.dp, bottom = 36.dp)
            ) {
                Column {
                    // Back button (White circle with dark arrow)
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .clickable { onBackClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Canvas(modifier = Modifier.size(20.dp)) {
                            val stroke = 2.5.dp.toPx()
                            val color = TextDark
                            val w = size.width
                            val h = size.height

                            drawLine(
                                color = color,
                                start = Offset(w, h / 2f),
                                end = Offset(0f, h / 2f),
                                strokeWidth = stroke,
                                cap = StrokeCap.Round
                            )
                            val arrowHead = Path().apply {
                                moveTo(w * 0.35f, h * 0.15f)
                                lineTo(0f, h / 2f)
                                lineTo(w * 0.35f, h * 0.85f)
                            }
                            drawPath(
                                path = arrowHead,
                                color = color,
                                style = Stroke(width = stroke, cap = StrokeCap.Round, join = StrokeJoin.Round)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Title depends on mode
                    Crossfade(targetState = mode, label = "HeaderTitle") { targetMode ->
                        Text(
                            text = if (targetMode == AuthMode.LOG_IN) "Welcome back!" else "Create Account",
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = (-0.5).sp
                        )
                    }
                }
            }

            // Bottom Form Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Tab / Toggle Selector ("Log In" vs "Register")
                AuthTabToggle(
                    selectedMode = mode,
                    onModeSelected = { mode = it }
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Input Fields
                if (mode == AuthMode.REGISTER) {
                    AuthInputField(
                        label = "FULL NAME",
                        value = fullName,
                        onValueChange = { fullName = it },
                        placeholder = "Enter your full name"
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                AuthInputField(
                    label = "MOBILE NUMBER",
                    value = mobileNumber,
                    onValueChange = { mobileNumber = it },
                    placeholder = "Enter mobile number",
                    keyboardType = KeyboardType.Phone
                )

                Spacer(modifier = Modifier.height(16.dp))

                AuthInputField(
                    label = "PASSWORD",
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "Enter password",
                    isPassword = true
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Primary Red Button
                Button(
                    onClick = { onSubmit(mode, role, fullName, mobileNumber, password) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFC41212)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = if (mode == AuthMode.LOG_IN) "Log In" else "Create Account",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Divider with "or"
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = Color(0xFFD0D0D0),
                        thickness = 1.dp
                    )
                    Text(
                        text = "or",
                        color = Color(0xFF888888),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = Color(0xFFD0D0D0),
                        thickness = 1.dp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // "Continue with Google" Button
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onGoogleSignIn() },
                    color = Color(0xFFE0E0E0)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Colored Google 'G' icon drawing
                        Canvas(modifier = Modifier.size(18.dp)) {
                            val w = size.width
                            val h = size.height
                            val stroke = 3.dp.toPx()
                            val color = Color(0xFF4285F4)

                            drawCircle(
                                color = color,
                                radius = w * 0.4f,
                                style = Stroke(width = stroke)
                            )
                            drawLine(
                                color = Color(0xFFEA4335),
                                start = Offset(w * 0.5f, h * 0.5f),
                                end = Offset(w * 0.9f, h * 0.5f),
                                strokeWidth = stroke
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "Continue with Google",
                            color = Color(0xFF444444),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun AuthTabToggle(
    selectedMode: AuthMode,
    onModeSelected: (AuthMode) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFE2E2E2))
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            // Log In Tab
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        if (selectedMode == AuthMode.LOG_IN) MaroonPrimary else Color.Transparent
                    )
                    .clickable { onModeSelected(AuthMode.LOG_IN) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Log In",
                    color = if (selectedMode == AuthMode.LOG_IN) Color.White else Color(0xFF555555),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Register Tab
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        if (selectedMode == AuthMode.REGISTER) MaroonPrimary else Color.Transparent
                    )
                    .clickable { onModeSelected(AuthMode.REGISTER) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Register",
                    color = if (selectedMode == AuthMode.REGISTER) Color.White else Color(0xFF555555),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun AuthInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            color = Color(0xFF555555),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(8.dp)),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            placeholder = {
                if (placeholder.isNotEmpty()) {
                    Text(
                        text = placeholder,
                        color = Color(0xFFAAAAAA),
                        fontSize = 14.sp
                    )
                }
            },
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = MaroonPrimary,
                unfocusedBorderColor = Color(0xFFCCCCCC),
                focusedTextColor = TextDark,
                unfocusedTextColor = TextDark
            )
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AuthScreenLogInPreview() {
    HandyHubTheme {
        AuthScreen(initialMode = AuthMode.LOG_IN)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AuthScreenRegisterPreview() {
    HandyHubTheme {
        AuthScreen(initialMode = AuthMode.REGISTER)
    }
}
