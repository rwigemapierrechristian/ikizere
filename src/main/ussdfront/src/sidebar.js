import React from "react";
import './sidebar.css'
function sidebar()
{
return(
    <div className="sidebar p-2 vh-100" id="sidebar">
        <div className="m-2">
            <i className="bi bi-bootstrap-fill me-2 fs-4"></i>
            <span className="brand-name fs-8">Neon mobiles</span>
        </div>
        
        <hr className="text-dark"  />

        <div className="list-group list-group-flush" >
            <a id="sidebar" className="list-group-item py-2"  >
                <i className="bi bi-speedometer2 fs-5 me-2"></i>
                <span className="fs-5">Dashboard</span>
            </a>
            <a id="sidebar" className="list-group-item py-2" >
                <i className="bi bi-clipboard-data fs-4 me-2"></i>
                <span className="fs-5">Post</span>
            </a>
            <a id="sidebar" className="list-group-item py-2" >
                <i className="bi bi-power fs-5 me-2"></i>
                <span className="fs-5">logout</span>
            </a>
        </div>
        </div>

        
)
 
}
export default sidebar;