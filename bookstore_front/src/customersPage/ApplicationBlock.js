const ApplicationBlock = ({application}) => {



    const replyToApplication = async (res) => {
        const requestParam = {
            method: "POST"
        }
        try {
            const response = await fetch(`http://localhost:8081/api/v1/customer/replyToApplication?applicationId=${application?.id}&response=${res}`, requestParam);
            if (response.ok) {
                const result = await response.text();
                alert(result);
                window.location.reload();
            } else {
                const result = await response.text();
                alert(result);
            }
        } catch (error) {
            console.log("Ошибка сети: " + error);
        }
    };

    return (
        <div className={'review_block'}>
            <p><strong>Заявка от: </strong> {application?.applicant?.surname} {application?.applicant?.name} {application?.applicant?.patronymic}</p>
            <button className='button' style={{marginTop: "10px", marginRight: "10px"}} onClick={() => {replyToApplication("yes")}}>Принять заявку</button>
            <button className='button' style={{marginTop: "10px"}} onClick={() => {replyToApplication("no")}}>Отклонить заявку</button>
        </div>
    )

}

export default ApplicationBlock