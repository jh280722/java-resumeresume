import React, {Component} from 'react';
import ApiService from '../../ApiService';

class EditItemComponent extends Component{

    constructor(props){
        super(props);

        this.state ={
            id:'',
            type:'',
            name:'',
            value:'',
            message:null
        }
    }

    componentDidMount(){
        this.loadItem();
    }

    loadItem = () => {
        ApiService.fetchItemByID(window.localStorage.getItem("itemID"))
        .then(res => {
            let item = res.data;
            this.setState({
                id: item.id,
                type: item.type,
                name: item.name,
                value: item.value,
            })
        })
        .catch(err=>{
            console.log('loadItem() 에러', err);
        });
    }

    onChange = (e) => {
        this.setState({
            [e.target.name] : e.target.value
        });
    }

    saveItem = (e) => {
        e.preventDefault();

        let item = {
            id: this.state.id,
            type: this.state.type,
            name: this.state.name,
            value: this.state.value,
        }

        ApiService.editItem(item)
        .then(res => {
            this.setState({
                message : item.name + '의 정보가 수정되었습니다.'
            })
            this.props.history.push('/items');
        })
        .catch(err => {
            console.log('saveItem() 에러', err);
        })
    }
    render(){
        return(
            <div>
                <h2>Edit Item</h2>
                <form>
                    <div>
                        <label>Type:</label>
                        <input type="text" placeholder="Edit item type" name="type"
                        value={this.state.type} onChange={this.onChange} />
                    </div>

                    <div>
                        <label>Name:</label>
                        <input type="text" placeholder="Edit item name" name="name"
                        value={this.state.name} onChange={this.onChange} />
                    </div>

                    <div>
                        <label>Value:</label>
                        <input type="text" placeholder="Edit item value" name="value"
                        value={this.state.value} onChange={this.onChange} />
                    </div>

                    <button onClick={this.saveItem}>Save</button>
                </form>
            </div>
        );
    }
}

export default EditItemComponent;