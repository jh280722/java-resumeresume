import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import ApiService from '../../ApiService';
import { Close } from '@styled-icons/ionicons-sharp/Close';

const Box = styled.div`
    #boxName{
        color: #232323;
        font-weight : bolder;
        font-size : 23px;
    }
    #boxContents{
        border-top : 1px solid gray;
        border-bottom : 1px solid gray;
        margin-bottom : 10px;
        display : flex;
        justify-content : space-between;
        #textItem{
            display : flex;
            align-items : center;
            #textItemName{
                border-right : 1px solid gray;
                padding-right : 10px;
                padding-left : 10px;
                color: #3B3B3B;
                font-weight : bold;
                font-size : 18px;
            }
            #textItemValue{
                padding-right : 10px;
                padding-left : 10px;
                color: #3B3B3B;
                font-weight : normal;
                font-size : 15px;
            }
        }
    }
`;

const ItemDel = styled(Close)`
    color: #3B3B3B;
    width : 25px;
    height : 25px;
`;

const AddItem = styled.div`
    display:flex;
    flex-direction:column;
    align-items:center;
    justify-content : space-between;
    select{
        height : 40px;
        width : 100px;
        font-size : 15px;
        margin : 5px;
    }
`;

const TextDiv = styled.div`
    border-top : 1px solid gray;
    border-bottom : 1px solid gray;
    padding-top : 10px;
    padding-bottom : 10px;
    display : flex;
    justify-content : space-between;
    width : 100%;
    #name{
        height :30px;
        margin-right : 5px;
        flex: 1;
    }

    #value{
        height :30px;
        flex: 10;
    }

    button{
        flex: 1;
    }
`;

const Text = styled.div`
    display : flex;
    align-items : center;
    #name{
        border-right : 1px solid gray;
        padding-right : 10px;
        padding-left : 10px;
        color: #3B3B3B;
        font-weight : bold;
        font-size : 18px;
    }
    #value{
        padding-right : 10px;
        padding-left : 10px;
        color: #3B3B3B;
        font-weight : normal;
        font-size : 15px;
        word-break:break-all;
    }
`;

const TextArea = styled.div`
    display : flex;
    flex-direction:column;
    #name{
        margin-left : 10px;
        margin-bottom : 0px;
        color: #3B3B3B;
        font-weight : bold;
        font-size : 18px;
    }
    #value{
        padding-right : 10px;
        padding-left : 10px;
        color: #3B3B3B;
        font-weight : normal;
        font-size : 15px;
        word-break:break-all;
    }
`;

const Date = styled.div`
    display : flex;
    align-items : center;
    #name{
        border-right : 1px solid gray;
        padding-right : 10px;
        padding-left : 10px;
        color: #3B3B3B;
        font-weight : bold;
        font-size : 18px;
    }
    #value{
        padding-right : 10px;
        padding-left : 10px;
        color: #3B3B3B;
        font-weight : normal;
        font-size : 15px;
    }
`;

const Image = styled.div`
    display : flex;
    align-items : center;
    #name{
        border-right : 1px solid gray;
        padding-right : 10px;
        padding-left : 10px;
        color: #3B3B3B;
        font-weight : bold;
        font-size : 18px;
    }
    #value{
        padding-right : 10px;
        padding-left : 10px;
        color: #3B3B3B;
        font-weight : normal;
        font-size : 15px;
    }
`;

const Period = styled.div`
    display : flex;
    align-items : center;
    #name{
        border-right : 1px solid gray;
        padding-right : 10px;
        padding-left : 10px;
        color: #3B3B3B;
        font-weight : bold;
        font-size : 18px;
    }
    #value{
        padding-right : 10px;
        padding-left : 10px;
        color: #3B3B3B;
        font-weight : normal;
        font-size : 15px;
    }
`;

const Table = styled.div`
    display : flex;
    align-items : center;
    #name{
        border-right : 1px solid gray;
        padding-right : 10px;
        padding-left : 10px;
        color: #3B3B3B;
        font-weight : bold;
        font-size : 18px;
    }
    #value{
        padding-right : 10px;
        padding-left : 10px;
        color: #3B3B3B;
        font-weight : normal;
        font-size : 15px;
    }
`;

function ItemlistComponent(props) {
    const [state, setState] = useState({
        box: [],
        items: [],
        boxName: '',
        type: '',
        name: '',
        value: '',
        seq: '',
    });
    const [btnState, setBtnState] = useState(false);
    const [grab, setGrab] = useState(null);
    const [imageState, setImageState] = useState(null);
    const [tableRow, setTableRowState] = useState(0);
    const [tableCol, setTableColState] = useState(0);
    const [tableValue, setTableValueState] = useState([]);
    const [tableItems, setTableItemsState] = useState([]);

    useEffect(() => {
        ApiService.fetchBoxesByID(props.boxID)
            .then(res => {
                setState({
                    ...state,
                    box: res.data,
                    items: res.data.items,
                });
            })
            .catch(err => {
                console.log("reloadError!", err);
            })
    }, [btnState]);

    const deleteItem = (itemID) => {
        ApiService.deleteItem(itemID)
            .then(res => {
                setBtnState(!btnState); // onClick 이벤트 상태 변화
            })
            .catch(err => {
                console.log('deleteItem() 에러! ', err);
            })
    }

    const onChange = (e) => {
        setState({
            ...state,
            [e.target.name]: e.target.value
        });
    }

    const onRowChange = (e) => {
        setTableRowState(Number(e.target.value));
        setTableValueState([]);
        setBtnState(!btnState);
    }

    const onColChange = (e) => {
        setTableColState(Number(e.target.value));
        setTableValueState([]);
        setBtnState(!btnState);
    }

    const onValueChange = (e) => {
        setTableValueState({
            ...tableValue,
            [e.target.dataset.col + "/" + e.target.dataset.row]: e.target.value
        });
    }

    const onReset = () => {
        setState({
            ...state,
            type: '',
            name: '',
            value: '',
        })
    }

    const saveItem = (e) => {
        e.preventDefault();

        let targetBox = {
            id: e.target.dataset.boxid,
            name: e.target.dataset.boxname,
        }
        let item = {
            type: state.type,
            name: state.name,
            value: state.value,
            box: targetBox,
        }

        ApiService.addItem(item)
            .then(res => {
                onReset();
                setBtnState(!btnState);
            })
            .catch(err => {
                console.log('saveItem 에러', err);
            });
    }

    const saveTableItem = (e) =>{
        e.preventDefault();
        let targetBox = {
            id: e.target.dataset.boxid,
            name: e.target.dataset.boxname,
        }
        let itemData ={
            type: state.type,
            name: state.name,
            rowSize:tableRow,
            colSize:tableCol,
            box: targetBox,
        }
        ApiService.addItem(itemData)
            .then(res => {
                onReset();
                setBtnState(!btnState);
            })
            .catch(err => {
                console.log('saveItem 에러', err);
            });
        for(var i=1;i<tableRow+1;i++){
            for(var j = 1; j<tableCol+1;j++){
                let tableItem = {
                    tableRow:i,
                    tableCol:j,
                    value:tableValue[i+"/"+j],
                    item:itemData,
                }
                tableItems.push(tableItem);
            }
        }
        ApiService.addTableItem(tableItems)
            .then(res => {
                onReset();
                setBtnState(!btnState);
            })
            .catch(err => {
                console.log('addTableItem 에러', err);
            });
        console.log(tableItems);
    }

    const _onDragOver = e => {
        e.preventDefault();
    }

    const _onDragStart = e => {
        setGrab(e.target);
        e.dataTransfer.effectAllowed = "move";
        e.dataTransfer.setData("text/html", e.target);
    }

    const _onDragEnd = e => {
        e.dataTransfer.dropEffect = "move";
        setBtnState(!btnState);
    }

    const _onDrop = e => {
        try {
            let params = new URLSearchParams();
            let grabPosition = Number(grab.dataset.id);
            let targetPosition = e.currentTarget.dataset.position;

            params.append('itemId', grabPosition);
            params.append('seq', targetPosition);

            ApiService.dragItem(state.box, params);
        } catch (e) {
            console.log(e);
        }
    }

    return (
        <Box key={state.box.id}>
            <div id="boxName">{state.box.name}</div>
            <div>
                {state.items.map(item =>
                    <div
                        draggable
                        onDragOver={_onDragOver}
                        onDragStart={_onDragStart}
                        onDragEnd={_onDragEnd}
                        onDrop={_onDrop}
                        key={item.id}
                        data-id={item.id}
                        data-position={item.seq}
                        id="boxContents"
                    >
                        {{
                            text: (
                                <Text>
                                    <p id="name">{item.name}</p>
                                    <p id="value">{item.value}</p>
                                </Text>),
                            textArea: (<TextArea>
                                <p id="name">{item.name}</p>
                                <div id="value"><p>{item.value}</p></div>
                            </TextArea>),
                            date: (<Date>
                                <p id="name">{item.name}</p>
                                <p id="value">{item.value}</p>
                            </Date>),
                            image: (<Image>
                                <p id="name">{item.name}</p>
                                <p id="value">{item.value}</p>
                            </Image>),
                            period: (<Period>
                                <p id="name">{item.name}</p>
                                <p id="value">{item.value}</p>
                            </Period>),
                            table: (<Table>
                                <p id="name">{item.name}</p>
                                <p id="value">{item.value}</p>
                            </Table>),
                        }[item.type]}
                        <ItemDel onClick={() => deleteItem(item.id)}>Delete</ItemDel>
                    </div>
                )}
                <AddItem>
                    <select
                        id="typeSelect"
                        name={"type"}
                        value={state.type}
                        displayEmpty
                        onChange={onChange}
                        inputProps={{ 'aria-label': 'Without label' }}
                    >
                        <option value="">타입선택</option>
                        <option value={"text"}>짧은 글</option>
                        <option value={"textArea"}>긴 글</option>
                        <option value={"date"}>날짜</option>
                        <option value={"image"}>이미지</option>
                        <option value={"period"}>기간</option>
                        <option value={"table"}>표</option>
                    </select>
                    {{
                        text: (<TextDiv>
                            <input type="text" id="name" name={"name"} onChange={onChange} />
                            <input type="text" id="value" name={"value"} onChange={onChange} />
                            <button onClick={saveItem} data-boxid={state.box.id} data-boxname={state.box.name}>Save</button>
                        </TextDiv>),
                        textArea: (<>
                            <input type="text" name={"name"} value={state.name} onChange={onChange} />
                            <input type="text" name={"value"} value={state.value} onChange={onChange} />
                            <button onClick={saveItem} data-boxid={state.box.id} data-boxname={state.box.name}>Save</button>
                        </>),
                        date: (<>
                            <input type="text" name={"name"} value={state.name} onChange={onChange} />
                            <input type="text" name={"value"} value={state.value} onChange={onChange} />
                            <button onClick={saveItem} data-boxid={state.box.id} data-boxname={state.box.name}>Save</button>
                        </>),
                        image: (<>
                            <input type="text" name={"name"} value={state.name} onChange={onChange} />
                            <input type="file" accept="image/jpeg, image/jpg" onChange={imageState} />
                            <button onClick={saveItem} data-boxid={state.box.id} data-boxname={state.box.name}>Save</button>
                        </>),
                        period: (<>
                            <input type="text" name={"name"} value={state.name} onChange={onChange} />
                            <input type="text" name={"value"} value={state.value} onChange={onChange} />
                            <button onClick={saveItem} data-boxid={state.box.id} data-boxname={state.box.name}>Save</button>
                        </>),
                        table: (<>
                            <p>행</p><input type="text" name={"row"} value={tableRow} onChange={onRowChange} />
                            <p>열</p><input type="text" name={"col"} value={tableCol} onChange={onColChange} />
                            <table border="1">
                                {[...Array(tableCol)].map((colN, colIndex) =>
                                    <tr>
                                        {[...Array(tableRow)].map((rowN, rowIndex) =>
                                            <th><input type="text" data-col={colIndex + 1} data-row={rowIndex + 1} onChange={onValueChange} /></th>
                                        )}
                                    </tr>
                                )}
                            </table>
                            <button onClick={saveTableItem} data-boxid={state.box.id} data-boxname={state.box.name}>Save</button>
                        </>),
                    }[state.type]}
                </AddItem>
            </div>
        </Box>
    )
}
export default ItemlistComponent;