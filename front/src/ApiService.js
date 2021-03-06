import axios from 'axios';

const LOGIN_API_BASE_URL = "http://localhost:8080/login/token"
const USER_API_BASE_URL = "http://localhost:8080/users";
const ITEM_API_BASE_URL = "http://localhost:8080/items";
const BOX_API_BASE_URL = "http://localhost:8080/boxes";
const DOC_API_BASE_URL = "http://localhost:8080/documents";
const SORT_API_BASE_URL = "http://localhost:8080/sortations";
const TABLE_API_BASE_URL = "http://localhost:8080/tableItems"

class ApiService{

    createUser(user) {
        return axios.post(USER_API_BASE_URL, user);
    }

    login(userToken) {
        return axios.post(LOGIN_API_BASE_URL, userToken);
    }

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

    // tableItem 관리
    fetchTableItems() {
        return axios.get(TABLE_API_BASE_URL);
    }

    fetchTableItemByID(itemID) {
        return axios.get(TABLE_API_BASE_URL + '/' + itemID);
    }

    deleteTableItem(itemID) {
        return axios.delete(TABLE_API_BASE_URL + '/' + itemID);
    }

    addTableItem(tableItem) {
        return axios.post(TABLE_API_BASE_URL, tableItem);
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

    // sortations 관리
    fetchSortations() {
        return axios.get(SORT_API_BASE_URL);
    }

    fetchSortationsByID(sortID) {
        return axios.get(SORT_API_BASE_URL + '/' + sortID);
    }

    deleteSortation(sortID) {
        return axios.delete(SORT_API_BASE_URL + '/' + sortID);
    }

    addSortation(sortation) {
        return axios.post(SORT_API_BASE_URL, sortation);
    }

    // User 관리
    fetchUsers() {
        return axios.get(USER_API_BASE_URL);
    }

    fetchUserByID(userID) {
        return axios.get(USER_API_BASE_URL + '/' + userID);
    }

    // deleteSortation(sortID) {
    //     return axios.delete(SORT_API_BASE_URL + '/' + sortID);
    // }

    // addSortation(sortation) {
    //     return axios.post(SORT_API_BASE_URL, sortation);
    // }
}

export default new ApiService();