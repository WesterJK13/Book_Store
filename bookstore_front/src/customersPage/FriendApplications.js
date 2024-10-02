import {useContext, useEffect, useState} from "react";
import {CustomContext} from "../utils/Context";
import Navbar from "../navbar/Navbar";
import PageName from "../pages/PageName";
import ApplicationBlock from "./ApplicationBlock";

const FriendApplications = () => {

    const user = useContext(CustomContext);
    const [applications, setApplications] = useState([]);

    useEffect(() => {
        const fetchApplication = async () => {
            try {
                const response = await fetch(`http://localhost:8081/api/v1/customer/allApplications?myId=${user?.user?.id}`)
                if (response.ok) {
                    const result = await response.json();
                    setApplications(result);
                } else {
                    const result = await response.text();
                    console.error("Ошибка сети: " + result);
                }
            } catch (error) {
                console.error("Ошибка сети: " + error);
            }
        };
        fetchApplication();
    }, [user])

    const printApplicationBlock = (application) => {
        return(
            <ApplicationBlock application={application}/>
        )
    }


    return (
        <div>
            <Navbar/>
            <div className='page-content'>
                <PageName name={"Заявки в друзья"}/>
                {applications?.length > 0 &&
                    applications.map(application => {
                        return printApplicationBlock(application);
                    })
                }
            </div>
        </div>
    )

}

export default FriendApplications;