# SMS HTTP Server

An Android application that creates an HTTP server to send SMS messages and initiate phone calls remotely via HTTP POST requests.

## Features

- 📱 **SMS Sending**: Send text messages programmatically via HTTP requests
- 📞 **Phone Calls**: Automatically initiate phone calls after sending SMS
- 🌐 **HTTP Server**: Built-in HTTP server running on port 8080
- 🔒 **Permission Management**: Handles SMS and phone call permissions automatically

## Requirements

- Android device with API level 21 (Android 5.0) or higher
- SMS and phone call permissions
- Network connectivity

## Installation

### Option 1: Build from Source

1. **Clone the repository**:
   ```bash
   git clone https://github.com/susant123/SmsHttpServer.git
   cd SmsHttpServer
   ```

2. **Open in Android Studio**:
   - Open Android Studio
   - Select "Open an existing Android Studio project"
   - Navigate to the cloned directory and select it

3. **Build and Install**:
   - Connect your Android device or start an emulator
   - Enable USB debugging on your device
   - Click "Run" in Android Studio or use:
     ```bash
     ./gradlew installDebug
     ```

### Option 2: Install APK

If an APK is available, simply download and install it on your Android device (enable "Install from unknown sources" in settings if needed).

## Usage

### 1. Launch the Application

- Open the "SMS HTTP Server" app on your Android device
- Grant the required permissions when prompted:
  - **Send SMS**: Required to send text messages
  - **Phone**: Required to make phone calls
- The HTTP server will automatically start on port 8080

### 2. Send SMS via HTTP Request

Make a POST request to your Android device's IP address on port 8080:

**Endpoint**: `http://[DEVICE_IP]:8080/`

**Method**: POST

**Content-Type**: application/json

**Request Body**:
```json
{
  "to": "+1234567890",
  "message": "Hello from SMS HTTP Server!"
}
```

### 3. Example Usage

#### Using cURL:
```bash
curl -X POST http://192.168.1.100:8080/ \
  -H "Content-Type: application/json" \
  -d '{
    "to": "+1234567890",
    "message": "Test message from HTTP server"
  }'
```

#### Using Python:
```python
import requests
import json

url = "http://192.168.1.100:8080/"
data = {
    "to": "+1234567890",
    "message": "Test message from Python"
}

response = requests.post(url, json=data)
print(response.text)
```

#### Using JavaScript (Node.js):
```javascript
const axios = require('axios');

const sendSMS = async () => {
  try {
    const response = await axios.post('http://192.168.1.100:8080/', {
      to: '+1234567890',
      message: 'Test message from Node.js'
    });
    console.log(response.data);
  } catch (error) {
    console.error('Error:', error);
  }
};

sendSMS();
```

## Configuration

### Finding Your Device IP Address

1. **Via Settings**:
   - Go to Settings → Wi-Fi
   - Tap on your connected network
   - Look for "IP Address"

2. **Via ADB**:
   ```bash
   adb shell ip addr show wlan0
   ```

### Port Configuration

The server runs on port 8080 by default. To change the port:

1. Open `MainActivity.kt`
2. Modify the port number in this line:
   ```kotlin
   server = SmsHttpServer(this, 8080) // Change 8080 to your desired port
   ```

## API Reference

### Send SMS Endpoint

- **URL**: `/`
- **Method**: `POST`
- **Content-Type**: `application/json`

#### Request Parameters

| Parameter | Type   | Required | Description                    |
|-----------|--------|----------|--------------------------------|
| to        | string | Yes      | Phone number (with country code) |
| message   | string | Yes      | SMS message content            |

#### Response

- **Success**: `200 OK` with message "SMS Sent and Call Initiated"
- **Error**: `400 Bad Request` for invalid requests

#### Example Request

```json
{
  "to": "+1234567890",
  "message": "Your verification code is: 123456"
}
```

## Permissions

The app requires the following permissions:

- `SEND_SMS`: To send text messages
- `CALL_PHONE`: To initiate phone calls
- `INTERNET`: To run the HTTP server

These permissions are automatically requested when the app starts.

## Security Considerations

⚠️ **Important Security Notes**:

- This app creates an open HTTP server on your device
- Anyone on your network can send SMS messages through your device
- Consider implementing authentication for production use
- Be aware of SMS costs from your carrier
- Monitor usage to prevent abuse

## Troubleshooting

### Common Issues

1. **Server not responding**:
   - Check if the app is running in the foreground
   - Verify your device's IP address
   - Ensure your device and client are on the same network

2. **SMS not sending**:
   - Verify SMS permissions are granted
   - Check if you have sufficient SMS credit/plan
   - Ensure the phone number format is correct (include country code)

3. **Calls not working**:
   - Grant phone call permissions
   - Check if the device supports making calls

### Logs

To view app logs:
```bash
adb logcat | grep SmsHttpServer
```

## Development

### Project Structure

```
app/
├── src/main/
│   ├── java/com/example/smshttpserver/
│   │   ├── MainActivity.kt          # Main activity
│   │   └── SmsHttpServer.kt         # HTTP server implementation
│   ├── res/
│   │   └── layout/
│   │       └── activity_main.xml    # Main layout
│   └── AndroidManifest.xml          # App permissions and config
├── build.gradle                     # App dependencies
└── ...
```

### Dependencies

- **NanoHTTPD**: Lightweight HTTP server for Android
- **Android SMS Manager**: For sending SMS messages
- **Android Intent**: For making phone calls

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is open source. Please check the repository for license details.

## Disclaimer

This application is for educational and development purposes. Users are responsible for:
- Complying with local laws and regulations regarding SMS and phone calls
- SMS charges from their mobile carrier
- Securing their device and network
- Preventing unauthorized access to the HTTP server

Use responsibly and at your own risk.

---

**Author**: Susanta Kumar Behera  
**Email**: susanta.behera09@gmail.com
