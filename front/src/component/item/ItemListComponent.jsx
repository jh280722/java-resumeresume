import React, {useState, useEffect} from 'react';
import ApiService from '../../ApiService';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import Button from '@material-ui/core/Button';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import FormHelperText from '@material-ui/core/FormHelperText';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';

function ItemlistComponent(props){
    const [state, setState] = useState({
        box: [],
        items: [],
        boxName : '',
        type:'',
        name:'',
        value:'',
        seq:'',
    });
    const [btnState, setBtnState] = useState(false);

    useEffect(() => {
        ApiService.fetchBoxesByID(props.boxID)
        .then(res => {
            setState({
                ...state,
                box : res.data,
                items: res.data.items,
            });
        })
        .catch(err => {
            console.log("reloadError!",err);
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
            [e.target.name] : e.target.value
        });
    }
    
    const onReset = () => {
        setState({
            ...state,
            type:'',
            name:'',
            value:'',
            seq:'',
        })
    }

    const saveItem = (e) =>{
        e.preventDefault();

        let targetBox ={
            id: e.target.dataset.boxid,
            name : e.target.dataset.boxname,
        }
        let item ={
            type: state.type,
            name: state.name,
            value: state.value,
            seq: state.seq,
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

    return(
        <>
            <Grid container spacing={2}>
                    <Grid item xs={12}>
                        <Paper>
                            <div key={state.box.id}>
                                <h2>{state.box.name}</h2>
                                <table>
                                    <thead>
                                        <tr>
                                            <th>Type</th>
                                            <th>Name</th>
                                            <th>Value</th>
                                            <th>Seq</th>
                                            <th>Edit</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {state.items.map(item =>
                                            <tr key={item.key}>
                                                <td>{item.type}</td>
                                                <td>{item.name}</td>
                                                <td>{item.value}</td>
                                                <td>{item.seq}</td>
                                                <td>
                                                    <button onClick={()=>deleteItem(item.id)}>Delete</button>
                                                </td>
                                            </tr>
                                            )}
                                        <tr>
                                            <td>
                                                <FormControl variant="outlined">
                                                    <Select
                                                        name={"type"}
                                                        value={state.type}
                                                        displayEmpty
                                                        onChange={onChange}
                                                        inputProps={{ 'aria-label': 'Without label' }}
                                                    >   
                                                        <MenuItem value="">
                                                            <em>None</em>
                                                        </MenuItem>
                                                        <MenuItem value={"text"}>text</MenuItem>
                                                        <MenuItem value={"textArea"}>textArea</MenuItem>
                                                        <MenuItem value={"date"}>date</MenuItem>
                                                        <MenuItem value={"image"}>image</MenuItem>
                                                        <MenuItem value={"period"}>period</MenuItem>
                                                    </Select>
                                                </FormControl>
                                                {/* <input type="text" placeholder="input item type" name={"type"} value={this.state.type} onChange={this.onChange} /> */}
                                            </td>
                                            <td>
                                                <input type="text" placeholder="input item name" name={"name"} value={state.name} onChange={onChange} />
                                            </td>
                                            <td>
                                                <input type="text" placeholder="input item value" name={"value"} value={state.value} onChange={onChange}/>
                                            </td>
                                            <td>
                                                <input type="text" placeholder="input seq" name={"seq"} value={state.seq} onChange={onChange}/>
                                            </td>
                                            <td>
                                                <button onClick={saveItem} data-boxid={state.box.id} data-boxname={state.box.name}>Save</button>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </Paper>
                    </Grid>
                </Grid>
            </>
    )
}
export default ItemlistComponent;