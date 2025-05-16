<template>
  <div class="login-container">
    <div class="login-card">
      <h1>Welcome to Money Manager</h1>
      <p class="welcome-message">Sign in to track your finances and reach your goals</p>
      <h2>Sign In</h2>
      <div class="social-login">
        <button @click="loginWithGoogle" class="google-login-btn">
          <img src="https://itviec.com/assets/google_logo-af373a5e64715e7d4fcdea711f96995f7fd7a49725b3dd8910d4749b74742cb2.svg" alt="Google logo" />
          Continue with Google
        </button>
      </div>
      <div class="divider">
        <span>or</span>
      </div>
      <form class="email-login">
        <div class="form-group">
          <label>Email</label>
          <input type="email" placeholder="Enter your email" required>
        </div>
        <div class="form-group">
          <label>Password</label>
          <input type="password" placeholder="Enter your password" required>
        </div>
        <button type="submit" class="login-btn">Sign In</button>
      </form>
      <div class="footer-links">
        <a href="#">Forgot password?</a>
        <span>•</span>
        <a href="#">Create account</a>
      </div>
    </div>
  </div>
</template>

<script>
import { API_BASE_URL } from '../config.js';
import axios from 'axios';

export default {
  name: 'Login',
  created() {
    this.handleOAuthCallback();
  },
  methods: {
    loginWithGoogle() {
      window.location.href = `${API_BASE_URL}/oauth2/authorization/google`;
    },
    async handleOAuthCallback() {
      const urlParams = new URLSearchParams(window.location.search);
      const code = urlParams.get('code');
      
      if (code) {
        try {
          const response = await axios.get(`http://localhost:8081/oauth2/authorization/google?code=${code}`);
          console.log('OAuth2 callback response:', response.data);
          localStorage.setItem('token', response.data.token);
          
          // Fetch user profile after successful login
          const profileResponse = await axios.get(`http://localhost:8081/api/user/profile`, {
            headers: { Authorization: `Bearer ${response.data.token}` }
          });
          localStorage.setItem('user', JSON.stringify(profileResponse.data));
          
          console.log('Login successful', response.data.token);
          this.$router.push('/transactions');
        } catch (error) {
          console.error('OAuth2 callback error:', error);
        }
      }
    }
  }
};
</script>

<style scoped>
.login-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background-color: #ffffff;
}

.login-card {
  width: 400px;
  padding: 40px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

h1 {
  text-align: center;
  margin-bottom: 30px;
  color: #333;
  font-size: 24px;
}

.social-login {
  margin-bottom: 20px;
}

.google-login-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  padding: 12px;
  background: white;
  color: #5f6368;
  border: 1px solid #dadce0;
  border-radius: 4px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.3s;
}

.google-login-btn:hover {
  background: #f7f8f8;
}

.google-login-btn img {
  width: 18px;
  margin-right: 8px;
}

.divider {
  display: flex;
  align-items: center;
  margin: 20px 0;
  color: #999;
  font-size: 14px;
}

.divider::before, .divider::after {
  content: "";
  flex: 1;
  border-bottom: 1px solid #e0e0e0;
}

.divider span {
  padding: 0 10px;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-size: 14px;
  color: #555;
}

.form-group input {
  width: 100%;
  padding: 12px;
  border: 1px solid #dadce0;
  border-radius: 4px;
  font-size: 14px;
  color: #5f6368;
}

.login-btn {
  width: 100%;
  padding: 12px;
  background: #4285F4;
  color: white;
  border: 1px solid #4285F4;
  border-radius: 4px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  margin-top: 10px;
  transition: background-color 0.3s;
}

.login-btn:hover {
  background: #3367d6;
}

.welcome-message {
  text-align: center;
  color: #5f6368;
  margin-bottom: 30px;
  font-size: 16px;
}

.footer-links {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
  font-size: 14px;
  color: #999;
}

.footer-links a {
  color: #4285F4;
  text-decoration: none;
  margin: 0 5px;
}

.footer-links a:hover {
  text-decoration: underline;
}
</style>