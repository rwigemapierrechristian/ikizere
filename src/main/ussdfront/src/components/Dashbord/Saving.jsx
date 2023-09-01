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
const Savings = () =>{
   
  const [inputData, setInputData] = useState({
    phoneNumber: '',
    name: '',
    month: '',
    amount: '',
  });

  const [modifiedData, setModifiedData] = useState({
    phoneNumber: '',
    name: '',
    month: '',
    amount: '',
  });

  const [data, setData] = useState([]);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const response = await axios.get('/ussd/allSavings');
      setData(response.data);
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };



  const handleUpdate = (rowData) => {
    setModifiedData({ ...rowData });
  };

  const handleUpdateSubmit = () => {
    axios
      .put('/ussd/updateSavings', modifiedData)
      .then((response) => {
        alert('Update successful');
        fetchData();
      })
      .catch((error) => {
        console.error('Error:', error);
      });
  };

  const handleDelete = async (number) => {
    try {
      await axios.delete(`/ussd/savings/${number}`);
      alert('User Deleted successfully');
      fetchData();
    } catch (error) {
      console.error('Error deleting user:', error);
    }
  };

    const menuRef = useRef(null);

    const toggleMenu = () => menuRef.current.classList.toggle("show-menu");
    
    const handleSubmit = (event) => {
        event.preventDefault();
        axios
          .post('/ussd/postSavings', inputData)
          .then((res) => {
            alert('User savings added successfully');
            fetchData();
          })
          .catch((err) => console.log(err));
      };
    const [post, setPost] = useState(false);
    const togglePost = () => {
        setPost(!post);
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

                <div className="table-container">
                    <div className="addModal">
                        <button onClick={togglePost}>Add+</button>
                    </div>
                    <table>
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>Number</th>
                                <th>Name</th>
                                <th>Month</th>
                                <th>Amount</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                        {data.map((item) =>
            modifiedData.number === item.number ? (
              <tr key={index}>
                <td>{index++}</td>
                <td>
                  <input type="text" className='width-12px' value={modifiedData.phoneNumber} onChange={(e) =>setModifiedData({...modifiedData, phoneNumber: e.target.value,}) }/>
                </td>
                <td>
                  <input type="text" value={modifiedData.name} onChange={(e) => setModifiedData({ ...modifiedData,name: e.target.value,}) } />
                </td>
                <td>
                  <input type="text" value={modifiedData.month} onChange={(e) => setModifiedData({  ...modifiedData, month: e.target.value, }) } />
                </td>
                <td>
                  <input  type="text"  value={modifiedData.amount}  onChange={(e) => setModifiedData({...modifiedData, amount: e.target.value,})}/>
                </td>
                <td  className='buttons'> <GrUpdate className='update' onClick={handleUpdateSubmit} /> 
                             <RiDeleteBin5Fill className='delete' onClick={() => handleDelete(item.phoneNumber)}/> </td>    
                </tr>
            ) : (
                <tr key={index}>
                <td className='data'>{index++}</td>
                <td className='data'>{item.phoneNumber}</td>
                <td className='data'>{item.name}</td>
                <td className='data'>{item.month}</td>
                <td className='data'>{item.amount}</td>
                                <td  className='buttons '> <GrUpdate className='update' onClick={() => handleUpdate(item)} /> 
                             <RiDeleteBin5Fill className='delete' onClick={() => handleDelete(item.phoneNumber)}/> </td>                           
                            </tr>
                                        )
                                        )}
                        </tbody>
                    </table>
                </div>
                <div className="footer-others">
                    <p>&copy; copyright by Ikizere Savings</p>
                </div>

            </div>
            {post && (
        <div className="post">
          <div onClick={togglePost} className="overlay"></div>
          <div className="Post-content">
            <h2>make savings</h2>
            <form className="needs-validation" onSubmit={handleSubmit}>
                <div className="form-Container">

                
              <div className="form-group was-validated mb-2">
                <label htmlFor="name" className="form-label">
                  number
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
                  Please Enter the number to save from
                </div>
              </div>
              <div className="form-group was-validated mb-2">
                <label htmlFor="email" className="form-label">
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
                  Please Enter the users name
                </div>
              </div>
              <div className="form-group was-validated mb-2">
                <label htmlFor="email" className="form-label">
                  month
                </label>
                <input
                  type="text"
                  className="form-control"
                  onChange={(e) =>
                    setInputData({ ...inputData, month: e.target.value })
                  }
                  required
                />
                <div className="invalid-feedback">
                  Please Enter the month/year you are saving for
                </div>
              </div>
              <div className="form-group was-validated mb-2">
                <label htmlFor="email" className="form-label">
                  amount
                </label>
                <input
                  type="text"
                  className="form-control"
                  onChange={(e) =>
                    setInputData({ ...inputData, amount: e.target.value })
                  }
                  required
                />
                <div className="invalid-feedback">
                  Please Enter the amount you wish to save
                </div>
              </div>
          </div>
              <button
                type="submit"
                className="btn btn-outline-success block w-100 mt-2 "
              >
                Save
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
export default Savings