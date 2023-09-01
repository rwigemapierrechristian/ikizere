import React, { useState } from "react";

import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-icons/font/bootstrap-icons.css'
import Sidebar from './sidebar';
import Dash from "./components/Dashbord/Dash";

function dashboard()
{
    const [toggle,SetToggle] = useState(true)
    const Toggle = () => {
        SetToggle(!toggle)
    }
    return (
        <>
        <Dash />
        </>
    )
}

export default dashboard;
