import './dash.css'
import logo from "../../assets/profile.jpg"
import logo1 from "../../assets/logo.png"
import React, { useEffect, useState } from 'react';
import { useRef } from 'react'
import {GrUpdate} from "react-icons/gr"
import {RiDeleteBin5Fill} from 'react-icons/ri'
import {MdEnergySavingsLeaf,MdSpaceDashboard,MdGroups,MdPendingActions} from 'react-icons/md'
import {AiFillPayCircle,AiOutlineTransaction} from 'react-icons/ai'
import { Link } from "react-router-dom"
import axios from './axiosConfig';
const Savings = () =>{
   
  const [inputData, setInputData] = useState({
    Name:'',
    Number:'',
    Members:'',
    TotalSavings:'',
  });

  const [modifiedData, setModifiedData] = useState({
    Name:'',
    Number:'',
    Members:'',
    TotalSavings:'',
  });

  const [data, setData] = useState([]);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const response = await axios.get('/ussd/allGroups');
      setData(response.data);
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };



  const handleDelete = async (number) => {
    try {
      await axios.delete(`/ussd/groups/${number}`);
      alert('Group deleted successfully');
      fetchData();
    } catch (error) {
      console.error('Error deleting user:', error);
    }
  };

    const menuRef = useRef(null);

    const toggleMenu = () => menuRef.current.classList.toggle("show-menu");
  
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
                    {/* <div className="addModal">
                        <button onClick={togglePost}>Add+</button>
                    </div> */}
                    <table>
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>Name</th>
                                <th>Number</th>
                                <th>Members</th>
                                <th>Total Savings</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                        {data.map((item) =>

                <tr key={index}>
                <td className='data'>{index++}</td>
                <td className='data'>{item.name}</td>
                <td className='data'>{item.number}</td>
                <td className='data'>{item.members}</td>
                <td className='data'>{item.totalSavings}</td>
                                <td  className='buttons '>  
                             <RiDeleteBin5Fill className='delete' onClick={() => handleDelete(item.number)}/> </td>                           
                            </tr>
                                        )
                                        }
                        </tbody>
                    </table>
                </div>
                <div className="footer-others">
                    <p>&copy; copyright by Ikizere Savings</p>
                </div>
            </div>
        </div>
    </section>
    </>
)
}
export default Savings