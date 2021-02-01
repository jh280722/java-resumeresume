import React, {Component} from 'react';
import ApiService from '../../ApiService';

class AddItemComponent extends Component{

    constructor(props){
        super(props);

        this.state ={
            type:'',
            name:'',
            value:'',
            message:null
        }
    }

    onChange = (e) =>{
        this.setState({
            [e.target.name] : e.target.value
        })
    }

    // e.preventDefault() : submit, a 태그를 통한 페이지 이동, input 전송 등의 동작을 막아준다.
    saveItem = (e) =>{
        e.preventDefault();

        let item ={
            type: this.state.type,
            name: this.state.name,
            value: this.state.value,
        }

        ApiService.addItem(item)
        .then(res => {
            this.setState({
                message: item.name + '이 성공적으로 등록되었습니다.'
            })
            console.log(this.state.message);
            this.props.history.push('/items');
        })
        .catch(err => {
            console.log('saveItem() 에러', err);
        });
    }

    render(){
        return(
            <div>
                <h2>Add Item</h2>
                <form>
                    <div>
                        <label>Type:</label>
                        <input type="text" placeholder="please input item type" name="type"
                        value={this.state.type} onChange={this.onChange} />
                    </div>

                    <div>
                        <label>Name:</label>
                        <input type="text" placeholder="please input item name" name="name"
                        value={this.state.name} onChange={this.onChange} />
                    </div>

                    <div>
                        <label>Value:</label>
                        <input type="text" placeholder="please input item value" name="value"
                        value={this.state.value} onChange={this.onChange} />
                    </div>

                    <button onClick={this.saveItem}>Save</button>

                </form>
            </div>
        );
    }
}

export default AddItemComponent;