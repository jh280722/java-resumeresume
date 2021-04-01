import React, {useState, useEffect} from 'react';
import DocumentListComponent from './DocumentListComponent';
import ApiService from '../../ApiService';

function SortationListComponent(props){
    const [sortations, setSortations] = useState([]);
    const [sortationId, setSortationId] = useState("0");
    const [sortationName, setSortationName] = useState("");
    const [btnState, setBtnState] = useState(false); // onClick 이벤트 시 렌더링을 시키기 위한 상태 변화 확인용 state

    useEffect(() => {
        ApiService.fetchSortations()
        .then(res => {
            setSortations(res.data);
        })
        .catch(err => {
            console.log('reloadSortList() Error! ',err);
        })
    },[btnState]);

    const addSort = (e) =>{
        e.preventDefault();
        let sortation ={
            name: sortationName
        }
        ApiService.addSortation(sortation)
        .then(res => {
            setBtnState(!btnState); // onClick 이벤트 상태 변화
            setSortationName("");
        })
        .catch(err => {
            console.log('addSort() 에러', err);
        });
    }

    const deleteSort = (sortID) => {
        ApiService.deleteSortation(sortID)
        .then(res => {
            setBtnState(!btnState); // onClick 이벤트 상태 변화
        })
        .catch(err => {
            console.log('deleteSort() 에러! ', err);
        })
    }

    const onChangeSortName = (e) =>{
        setSortationName(e.target.value);
    }

    const changeSort = (e) =>{
        setSortationId(e.target.dataset.sortid);
        setBtnState(!btnState);
    }

    return (
        <div>
            {sortations.map(sortation =>
                <div key={sortation.id}>
                    <p onClick={changeSort} data-sortid={sortation.id}>{sortation.name}</p>
                    <button onClick={() => deleteSort(sortation.id)}>Sortation 삭제</button>
                </div>
            )}
            <input type="text" placeholder="항목 이름" name={"setSortationName"} value={sortationName} onChange={onChangeSortName} />
            <button onClick={addSort}>Sortation 추가</button>
        </div>
    )
}

export default SortationListComponent;