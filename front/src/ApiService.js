import axios from 'axios';

const ITEM_API_BASE_URL = "http://localhost:8080/items";
const BOX_API_BASE_URL = "http://localhost:8080/boxes";
const DOC_API_BASE_URL = "http://localhost:8080/documents";

class ApiService{
    // item 관리
    fetchItems(){
        return axios.get(ITEM_API_BASE_URL);
    }

    fetchItemByID(itemID){
        return axios.get(ITEM_API_BASE_URL + '/' + itemID);
    }

    deleteItem(itemID){
        return axios.delete(ITEM_API_BASE_URL + '/' + itemID);
    }

    addItem(item){
        return axios.post(ITEM_API_BASE_URL,item);
    }

    editItem(item){
        return axios.put(ITEM_API_BASE_URL + '/' + item.id, item);
    }

    // box 관리
    fetchBoxes(){
        return axios.get(BOX_API_BASE_URL);
    }

    fetchBoxesByID(boxID){
        return axios.get(BOX_API_BASE_URL + '/' + boxID);
    }

    deleteBox(boxID){
        return axios.delete(BOX_API_BASE_URL + '/' + boxID);
    }

    addBox(box){
        return axios.post(BOX_API_BASE_URL,box);
    }

    dragItem(box, params){
        return axios.put(BOX_API_BASE_URL + '/drag/' + box.id, params);
    }

    // document 관리
    fetchDocuments(){
        return axios.get(DOC_API_BASE_URL);
    }

    fetchDocumentsByID(docID){
        return axios.get(DOC_API_BASE_URL + '/' + docID);
    }

    deleteDocument(docID){
        return axios.delete(DOC_API_BASE_URL + '/' + docID);
    }

    addDocument(document){
        return axios.post(DOC_API_BASE_URL,document);
    }
}

export default new ApiService();