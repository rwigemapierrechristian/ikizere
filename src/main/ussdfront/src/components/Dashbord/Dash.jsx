import './dash.css'
import logo from "../../assets/profile.jpg"
import logo1 from "../../assets/logo.png"
import React, { useEffect, useState } from 'react';
import { useRef } from 'react'
import {GrUpdate} from "react-icons/gr"
import {RiDeleteBin5Fill} from 'react-icons/ri'
import {MdEnergySavingsLeaf,MdSpaceDashboard,MdPendingActions,MdGroups} from 'react-icons/md'
import {AiFillPayCircle,AiOutlineTransaction} from 'react-icons/ai'
import { Link } from "react-router-dom"
import axios from './axiosConfig';
const Dash = () =>{

    const [inputData, setInputData] = useState({
        name: '',
        phoneNumber: '',
        pin: '',
        depositAmount: '',
        language: '',
      });
    
      const [modifiedData, setModifiedData] = useState({
        name: '',
        phoneNumber: '',
        pin: '',
        depositAmount: '',
        language: '',
      });

      const [data, setData] = useState([]);
      const [totalData, setTotalData] = useState([]);
      const [totalSavings, setTotalSavings] = useState(0);

      useEffect(() => {
        fetchData();
        fetchTotalData();
      }, []);
    
      const fetchData = async () => {
        try {
          const response = await axios.get('/ussd/all');
          setData(response.data);
        } catch (error) {
          console.error('Error fetching data:', error);
        }
      };

      const fetchTotalData = async () => {
        try {
          const response = await axios.get('/ussd/allTotals');
          setTotalData(response.data);
        } catch (error) {
          console.error('Error fetching data:', error);
        }
      };
      

      useEffect(() => {
        const sum = data.reduce((acc, item) => acc + item.depositAmount, 0);
        setTotalSavings(sum);
      }, [data]);

      const [loanCount, setLoanCount] = useState(0);

      useEffect(() => {
        // Fetch data from Spring Boot backend
        axios.get('/ussd/allLoans')
          .then(response => {
            // Filter data to count "paid" loans
            const paidLoansCount = response.data.filter(item => item.status === 'paid').length;
            setLoanCount(paidLoansCount);
          })
          .catch(error => {
            console.error('Error fetching data:', error);
          });
      }, []);

      const [pendingCount, setpendingCount] = useState(0);

      useEffect(() => {
        // Fetch data from Spring Boot backend
        axios.get('/ussd/allLoans')
          .then(response => {
            // Filter data to count "paid" loans
            const paidLoansCount = response.data.filter(item => item.status === 'pending').length;
            setpendingCount(paidLoansCount);
          })
          .catch(error => {
            console.error('Error fetching data:', error);
          });
      }, []);

      const [overdueCount, setoverdueCount] = useState(0);

      useEffect(() => {
        // Fetch data from Spring Boot backend
        axios.get('/ussd/allLoans')
          .then(response => {
            // Filter data to count "paid" loans
            const paidLoansCount = response.data.filter(item => item.status === 'overdue').length;
            setoverdueCount(paidLoansCount);
          })
          .catch(error => {
            console.error('Error fetching data:', error);
          });
      }, []);

    const menuRef = useRef(null);

    const toggleMenu = () => menuRef.current.classList.toggle("show-menu");
    

    const [post, setPost] = useState(false);
    const togglePost = () => {
        setPost(!post);
      };

      const handleSubmit = (event) => {
        event.preventDefault();
        axios
          .post('/ussd/post', inputData)
          .then((res) => {
            alert('User added successfully');
            fetchData();
          })
          .catch((err) => console.log(err));
      };
    
      const handleUpdate = (rowData) => {
        setModifiedData({ ...rowData });
      };
    
      const handleUpdateSubmit = () => {
        axios
          .put('/ussd/update', modifiedData)
          .then((response) => {
            alert('Update successful');
            fetchData();
          })
          .catch((error) => {
            console.error('Error:', error);
          });
      };
    
      const handleDelete = async (phoneNumber) => {
        try {
          await axios.delete(`/ussd/${phoneNumber}`);
          alert('User Deleted successfully');
          fetchData();
        } catch (error) {
          console.error('Error deleting user:', error);
        }
      };

      let index = 1;
return(
    <>
    <section>
        <div className="dash-wraper">
        <div className="sidebar p-2 vh-100 " id="sidebar" ref={menuRef} onClick={toggleMenu} >
            <div className="logoo" >
                <img src={logo1} alt="" />
            </div>
        <div className="m-2">
            <span className="brand-name fs-8">Ikizere Savings</span>
        </div>
        
        <hr className="text-dark"  />

        <div className="list-group list-group-flush" >
            <a id="sidebar" className="list-group-item py-2"  >
                <Link to="/Dashboard">
                    <MdSpaceDashboard class= "icons"/>
                <span className="fs-5">Dashboard</span>
                </Link>
                
            </a>
            <a id="sidebar" className="list-group-item py-2" >
                 <Link to="/Savings">

                <MdEnergySavingsLeaf class= "icons"/>
                <span className="fs-5">Savings</span>
                </Link>
            </a>
            <a id="sidebar" className="list-group-item py-2" >
                <Link to="/loan">
                <AiFillPayCircle class= "icons"/>
                <span className="fs-5">loan</span>
                </Link>
                
            </a>
            <a id="sidebar" className="list-group-item py-2" >
                <Link to="/Transaction">

                <AiOutlineTransaction class= "icons"/>
                <span className="fs-5">Transction</span>
                </Link>
            </a>
            <a id="sidebar" className="list-group-item py-2" >
                <Link to="/Groups">

                <MdGroups  class= "icons"/>
                <span className="fs-5">Groups</span>
                </Link>
            </a>
            <a id="sidebar" className="list-group-item py-2" >
                <Link to="/Pending">
                
                <MdPendingActions class= "icons"/>
                <span className="fs-5">Pending</span>
                </Link>
            </a>
        </div>
        </div>
            <div className="body-wraper">
                <div className="header">
                    <div className="header-container">
                    <div className="btn-container">
                        <div className='img-container'>
                        <img src={logo} alt="" />
                    </div>
                    <button>Logout</button>
                    </div>
                    

                    </div>
                </div>

                <div className="card-wraper">
                    <div className="cards">
                        <h1>{loanCount}</h1>
                        <p className='payed'>Paid</p>
                    </div>
                    <div className="cards">
                        <h1>{pendingCount}</h1>
                        <p className='pending'>Pending</p>
                    </div>
                    <div className="cards">
                        <h1>{overdueCount}</h1>
                        <p className='overdue'>overdue</p>
                    </div>
                    <div className="cards">
                        {totalData.map((item) =><h1 className='total-title'> {item.total}</h1>)}
                        <p className='total-num'>total savings</p>
                    </div>
                </div>

                <div className="table-container">
                    <div className="addModal">
                        <button onClick={togglePost}>Add+</button>
                    </div>
                    <table>
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>Name</th>
                                <th>phoneNumber</th>
                                <th>pin</th>
                                <th>Group</th>
                                <th>Savings</th>
                                <th>language</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                        {data.map((item) =>
            modifiedData.phoneNumber === item.phoneNumber ? (
              <tr key={index}>
                <td>{index++}</td>
                <td>
                  <input type="text" className='width-12px' value={modifiedData.name} onChange={(e) =>setModifiedData({...modifiedData, name: e.target.value,}) }/>
                </td>
                <td>
                  <input type="text" value={modifiedData.phoneNumber} onChange={(e) => setModifiedData({ ...modifiedData,phoneNumber: e.target.value,}) } />
                </td>
                <td>
                  <input type="text" value={modifiedData.pin} onChange={(e) => setModifiedData({  ...modifiedData, pin: e.target.value, }) } />
                </td>
                <td>
                  <input  type="text"  value={modifiedData.depositAmount}  onChange={(e) => setModifiedData({...modifiedData, depositAmount: e.target.value,})}/>
                </td>
                <td>
                  <input
                    type="text"
                    value={modifiedData.language}
                    onChange={(e) =>   setModifiedData({  ...modifiedData, language: e.target.value, })} />
                </td>
                <td  className='buttons'> <GrUpdate className='update' onClick={handleUpdateSubmit} /> 
                             <RiDeleteBin5Fill className='delete' onClick={() => handleDelete(item.phoneNumber)}/> </td>    
                </tr>
            ) : (
                <tr key={index}>
                <td className='data'>{index++}</td>
                <td className='data'>{item.name}</td>
                <td className='data'>{item.phoneNumber}</td>
                <td className='data'>{item.pin}</td>
                <td className='data'>{item.savingsGroup}</td>
                <td className='data'>{item.depositAmount}</td>
                <td className='data'>{item.language}</td>
                                <td  className='buttons '> <GrUpdate className='update' onClick={() => handleUpdate(item)} /> 
                             <RiDeleteBin5Fill className='delete' onClick={() => handleDelete(item.phoneNumber)}/> </td>                           
                            </tr>
                                        )
                                        )}
                        </tbody>
                 
                    </table>
                </div>
                <div className="footer">
                    <p>&copy; copyright by Ikizere Savings</p>
                </div>

            </div>
            {post && (
        <div className="post">
          <div onClick={togglePost} className="overlay"></div>
          <div className="Post-content">
            <h2>Create a new user</h2>
            <form className="needs-validation" onSubmit={handleSubmit}>
                <div className="form-Container">

                
              <div className="form-group was-validated mb-2">
                <label htmlFor="name" className="form-label">
                  name
                </label>
                <input
                  type="text"
                  className="form-control"
                  onChange={(e) =>
                    setInputData({ ...inputData, name: e.target.value })
                  }
                  required
                />
                <div className="invalid-feedback">
                  Please Enter the name of the user
                </div>
              </div>
              <div className="form-group was-validated mb-2">
                <label htmlFor="email" className="form-label">
                  phone Number
                </label>
                <input
                  type="text"
                  className="form-control"
                  onChange={(e) =>
                    setInputData({ ...inputData, phoneNumber: e.target.value })
                  }
                  required
                />
                <div className="invalid-feedback">
                  Please Enter the phone Number
                </div>
              </div>
              <div className="form-group was-validated mb-2">
                <label htmlFor="email" className="form-label">
                  pin
                </label>
                <input
                  type="text"
                  className="form-control"
                  onChange={(e) =>
                    setInputData({ ...inputData, pin: e.target.value })
                  }
                  required
                />
                <div className="invalid-feedback">
                  Please Enter the pin
                </div>
              </div>
              <div className="form-group was-validated mb-2">
                <label htmlFor="email" className="form-label">
                  Savings
                </label>
                <input
                  type="text"
                  className="form-control"
                  onChange={(e) =>
                    setInputData({ ...inputData, Savings: e.target.value })
                  }
                  required
                />
                <div className="invalid-feedback">
                  Please Enter Savings
                </div>
              </div>
              <div className="form-group was-validated mb-2">
                <label htmlFor="email" className="form-label">
                  Language
                </label>
                <input
                  type="text"
                  className="form-control"
                  onChange={(e) =>
                    setInputData({
                      ...inputData,
                      language: e.target.value,
                    })
                  }
                  required
                />
                <div className="invalid-feedback">
                  Please Enter Language of preference
                </div>
              </div>
              </div>
              <button
                type="submit"
                className="btn btn-outline-success block w-100 mt-2 "
              >
                create
              </button>
              <button
                type="button"
                className=" clox"
                onClick={togglePost}
              >
                close
              </button>
              
            </form>
          </div>
        </div>
      )}
        </div>
    </section>
    </>
)
}
export default Dash