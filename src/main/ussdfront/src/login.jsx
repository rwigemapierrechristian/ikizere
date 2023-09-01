import React, { useState } from "react";
import { BrowserRouter as Router, Routes, Route, useNavigate } from "react-router-dom";
import axios from "./api/axiosConfig";
import './login.css'
import Dash from './dashboard'
import { PublicClientApplication } from '@azure/msal-browser';
import { MsalProvider, MsalAuthenticationTemplate, useMsal } from '@azure/msal-react';
import { loginRequest } from './api/authConfig'; // Define your login request scope
import msalConfig from './api/msalConfig';
import Savings from "./components/Dashbord/Saving.jsx"
import Loans from "./components/Dashbord/Loans.jsx"
import Transactions from "./components/Dashbord/Transactions.jsx"
import Groups from "./components/Dashbord/Groups"
import Pending from "./components/Dashbord/Pending"
const pca = new PublicClientApplication(msalConfig);

function MicrosoftLogin() {
  const { instance } = useMsal();
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      await instance.loginPopup(loginRequest);
      // If successful, the user is now authenticated and you can redirect them to the dashboard or perform other actions
      const account = instance.getActiveAccount();
      if (account) {
        const email = account.username;
        axios.post("/ussd/admin", {
          email: email
        })
          .then((response) => {
            if (response.data === "success") {
              navigate("/dash");
            } else {
              alert("Email verification failed");
            }
          })
          .catch((error) => {
            console.error("Error verifying email:", error);
          });
      }
    } catch (error) {
      console.error('Error signing in:', error);
    }
  };

  return (
    <button className="btn btn-outline-primary block w-100 mt-2" onClick={handleLogin}>Sign In with Microsoft Office</button>
  );
}

function Login() {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");

  const handleEmailChange = (event) => {
    setEmail(event.target.value);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    axios.post("/ussd/login", {
      username: email,
      // password: password,
    })
      .then((response) => {
        if (response.data === "success") {
          navigate("/dash");
        } else {
          alert("Login failed");
        }
      })
      .catch((error) => {
        console.error("Error verifying login credentials:", error);
      });
  };

  return (
    <div className="wrapper bg-Gainsboro d-flex align-items-center justify-content-center w-100">
      <div className="login">
        <h2 className="mb-3">Login</h2>
        <form className="needs-validation" onSubmit={handleSubmit}>
          <div className="form-group was-validated mb-2">
            <label htmlFor="email" className="form-label">
              Email Address
            </label>
            <input
              type="email"
              className="form-control"
              value={email}
              onChange={handleEmailChange}
              required
            />
            <div className="invalid-feedback">Please Enter your Email</div>
          </div>
          <div className="form-group mb-2 form-check mb-2">
            <input type="checkbox" className="form-check-input" />
            <label htmlFor="check" className="form-check-label">
              Remember me
            </label>
          </div>
          <button type="submit" className="btn btn-outline-success block w-100 mt-2">
            LOGIN
          </button>
          <div><p className="param">Or signIn with</p></div>
          <MicrosoftLogin />
        </form>
      </div>
    </div>
  );
}

function App() {
  return (
    <MsalProvider instance={pca}>
      <Router>
        <Routes>
          <Route path="/dash" element={<Dash />} />
          <Route path="/" element={<Login />} />
          <Route path="/Dashboard" element={<Dash />} />
          <Route path="/Savings" element={<Savings />} />
          <Route path="/Loan" element={<Loans />} />
          <Route path="/Transaction" element={<Transactions />} />
          <Route path="/Groups" element={<Groups />} />
          <Route path="/Pending" element={<Pending />} />
        </Routes>
      </Router>
    </MsalProvider>
  );
}

export default App;
