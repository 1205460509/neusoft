<template>
    <div class="body">
        <el-container>
            <el-header class="bgclr-dk-sp0 mb20 headerCoster">
                <el-breadcrumb separator-class="el-icon-arrow-right">
                    <el-breadcrumb-item>业务信息管理</el-breadcrumb-item>
                </el-breadcrumb>
            </el-header>
            <div class="pl20 pr20">
                   
                <el-card class="mb20 cardWidth">
                    <div slot="header">
                        <span>条件查询</span>
                    </div>
                        <el-form class="noMarginBottom" :inline="true" :model="formdata" ref="formdata">
                            <el-form-item label="业务类型" class="mb1" prop="type">
                                <el-select size="small" v-model.trim="formdata.type">
                                    <el-option label="电子病历" value="1">电子病历</el-option>
                                    <el-option label="健康档案" value="2">健康档案</el-option>
                                    <el-option label="全员人口" value="3">全员人口</el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="业务名称" class="mb1" prop="catagory">
                                <el-input size="small" placeholder="请输入业务名称" v-model.trim="formdata.catagory"></el-input>
                            </el-form-item>

                            <el-form-item class="mb1 fltRt">
                                <el-button size="small" class="bkmgColor" @click="resetMessage('formdata')" >重置</el-button>
                            </el-form-item>
                            <el-form-item class="mb1 fltRt">
                                <el-button size="small" type="primary" @click="serch(formdata)">查询</el-button>
                            </el-form-item>
                            
                        </el-form>
                </el-card>

                <el-card class="cardWidth">
                    <div slot="header">
                        <span>业务信息列表</span>
                    </div>
                    <span class="fltRt">
                        <el-button type="text" icon="iconfont icon-tianjia" @click="addValidFlag()" >新增</el-button>
                    </span>
                    
                    <span class="fltRt">
                        <el-button type="text" :disabled="btnEnable" icon="iconfont icon-bianji" @click="updateValidFlag(radio)">修改</el-button>
                    </span>
                    <el-table
                            ref="multipleTable"
                            border
                            stripe
                            height="335"
                            :data="getPageData()"
                            :header-cell-style="getRowClass"
                            tooltip-effect="dark"
                            >

                            <el-table-column width="30">
                                <template slot-scope="scope">
                                    <el-radio-group v-model="radio">
                                        <el-radio :label="scope.row.id" @change="updateEnable"></el-radio>
                                    </el-radio-group>
                                </template>
                            </el-table-column>
                            <el-table-column label="业务大类编码" prop="catagoryCode"></el-table-column>
                            <el-table-column label="业务大类名称" prop="catagory"></el-table-column>
                            <el-table-column label="业务类型" prop="type">
                                <template slot-scope="scope">
                                    <span v-if="scope.row.type=='1'">电子病历 </span>
                                    <span v-else-if="scope.row.type=='2'">健康档案</span>
                                    <span v-else-if="scope.row.type=='3'">全员人口</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="业务表名" prop="tableName"></el-table-column>
                            <el-table-column label="业务表描述" prop="tableDesc"></el-table-column>
                            <el-table-column label="时间字段" prop="timeColumn"></el-table-column>
                            <el-table-column label="是否统计业务量" prop="staticAmount">
                                <template slot-scope="scope">
                                    <span v-if="scope.row.staticAmount=='1'">统计 </span>
                                    <span v-else-if="scope.row.staticAmount=='2'">不统计</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="统计方式" prop="staticWay">
                                <template slot-scope="scope">
                                    <span v-if="scope.row.staticWay=='1'">按记录条数</span>
                                    <span v-else-if="scope.row.staticWay=='2'">按费用金额</span>
                                    <span v-else-if="scope.row.staticWay==''"></span>
                                </template>
                            </el-table-column>
                            <el-table-column label="分组字段" prop="groupColumn">
                                <template slot-scope="scope">
                                    <span v-if="scope.row.groupColumn=='1'">费用类别</span>
                                    <span v-else-if="scope.row.groupColumn==''"></span>
                                </template>
                            </el-table-column>
                            <el-table-column label="启用控制" prop="validFlag">
                                <template slot-scope="scope">
                                    <el-switch
                                    inactive-value="0"
                                    active-value="1"
                                    v-model="scope.row.validFlag"
                                    @change="editValidFlag(scope.row)">
                                    </el-switch>
                                </template>
                            </el-table-column>
                    </el-table>

                    <div class="mt15 textAlignRight">
                        <el-pagination
                                layout="total,sizes,prev, pager, next,jumper"
                                :total="tabledata.length"
                                :current-page.sync="currentPage"
                                :page-sizes="[5, 10, 15, 20]"
                                :page-size="pagesize">
                        </el-pagination>
                    </div>
                </el-card>
            </div>
            
            <!-- 添加弹窗 -->
            <el-dialog
                title="添加业务信息"
                :visible.sync="addVisible"
                width="48%"
                :before-close="dialogClose1"
                center>
                <div style="height:250px" class="elDiv">
                    <el-scrollbar style="height:100%">
                        <el-form
                        :model="addData"
                        ref="addData">
                            <el-row>
                                <el-col :span="12">
                                    <el-form-item label="业务表" prop='catagoryCode'>
                                        <el-select size="small" v-model="addData.catagoryCode" @change="selectId(addData.catagoryCode)">
                                            <el-option v-for="bs in business" :value="bs.catagoryCode" :label="bs.catagory" ></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="12">
                                    <el-form-item label="业务大类名称" prop='catagory'>
                                        <el-select size="small" v-model="addData.catagory">
                                            <el-option v-for="bs in business" :value="bs.catagory" :label="bs.catagory"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                            </el-row>

                            <el-row>
                                <el-col :span="12">
                                    <el-form-item label="业务类型" prop='type'>
                                        <el-select size="small" v-model="addData.type">
                                            <el-option :value="'1'" :label="'电子病历'"></el-option>
                                            <el-option :value="'2'" :label="'健康档案'"></el-option>
                                            <el-option :value="'3'" :label="'全员人口'"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="12">
                                    <el-form-item label="时间字段" prop='timeColumn'>
                                        <el-select size="small" v-model="addData.timeColumn">
                                            <el-option v-for="bs in business" :value="bs.timeColumn"  :label="bs.timeColumn"></el-option>
                                        </el-select>       
                                    </el-form-item>
                                </el-col>    
                            </el-row>
                            
                            <el-row>
                                <el-col :span="12">
                                    <el-form-item label="医疗/公卫" prop='medPh'>
                                        <el-select size="small" v-model="addData.medPh">
                                            <el-option :value="'1'" :label="'医疗'"></el-option>
                                            <el-option :value="'2'" :label="'公卫'"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="12">
                                    <el-form-item label="是否统计业务量" prop='staticAmount'>
                                        <el-radio-group v-model="addData.staticAmount" @change="change1">
                                            <el-radio size="small" label='1' value='1'>统计</el-radio>
                                            <el-radio size="small" label='2' value='2'>不统计</el-radio>
                                        </el-radio-group>
                                    </el-form-item>
                                </el-col>
                            </el-row>

                            <div v-if="show">
                                <el-row>
                                    <el-col :span="12">    
                                        <el-form-item label="统计方式" prop='staticWay'>
                                            <el-select size="small"  v-model="addData.staticWay">
                                                <el-option :value="'1'" :label="'按记录条数'"></el-option>
                                                <el-option :value="'2'" :label="'按费用金额'"></el-option>
                                            </el-select>
                                        </el-form-item>
                                    </el-col>    
                                    
                                    <el-col :span="12">
                                        <el-form-item label="费用金额字段" prop='feeColumn'>
                                            <el-select size="small" v-model="addData.feeColumn">
                                                <el-option :value="'1'" :label="'费用金额'"></el-option>
                                            </el-select>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                                <el-row>
                                    <el-col :span="12">     
                                        <el-form-item label="分组字段" prop='groupColumn'>
                                            <el-select size="small" v-model="addData.groupColumn">
                                                <el-option :value="'1'" :label="'费用类别'"></el-option>
                                            </el-select>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                            </div>
                            <div v-if="false">
                                <el-form-item label="业务表名" prop='tableName'>
                                    <input size="small" v-model="addData.tableName"></input>
                                </el-form-item>
                                <el-form-item label="业务表描述" prop='tableDesc'>
                                    <input size="small" v-model="addData.tableDesc"></input>
                                </el-form-item>
                            </div>
                        </el-form>
                    </el-scrollbar>
                </div>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="closeVisible1">取 消</el-button>
                    <el-button type="primary" @click="add(addData)">确 定</el-button>
                </span>
            </el-dialog>

            <!-- 修改弹窗 -->
            <el-dialog
                title="添加业务信息"
                :visible.sync="updateVisible"
                width="48%"
                :before-close="dialogClose2"
                center>
                <div style="height:250px" class="elDiv">
                    <el-scrollbar style="height:100%">
                        <el-form
                        :model="updateData"
                        ref="updateData">
                            <el-row>
                                <el-col :span="12">
                                    <el-form-item label="业务表" prop='catagoryCode'>
                                        <el-select size="small" v-model="updateData.catagoryCode" @change="selectId(updateData.catagoryCode)">
                                            <el-option v-for="bs in business" :value="bs.catagoryCode" :label="bs.catagory" ></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="12">
                                    <el-form-item label="业务大类名称" prop='catagory'>
                                        <el-select size="small" v-model="updateData.catagory">
                                            <el-option v-for="bs in business" :value="bs.catagory" :label="bs.catagory"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                            </el-row>

                            <el-row>
                                <el-col :span="12">
                                    <el-form-item label="业务类型" prop='type'>
                                        <el-select size="small" v-model="updateData.type">
                                            <el-option :value="'1'" :label="'电子病历'"></el-option>
                                            <el-option :value="'2'" :label="'健康档案'"></el-option>
                                            <el-option :value="'3'" :label="'全员人口'"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="12">
                                    <el-form-item label="时间字段" prop='timeColumn'>
                                        <el-select size="small" v-model="updateData.timeColumn">
                                            <el-option v-for="bs in business" :value="bs.timeColumn"  :label="bs.timeColumn"></el-option>
                                        </el-select>       
                                    </el-form-item>
                                </el-col>    
                            </el-row>
                            
                            <el-row>
                                <el-col :span="12">
                                    <el-form-item label="医疗/公卫" prop='medPh'>
                                        <el-select size="small" v-model="updateData.medPh">
                                            <el-option :value="'1'" :label="'医疗'"></el-option>
                                            <el-option :value="'2'" :label="'公卫'"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="12">
                                    <el-form-item label="是否统计业务量" prop='staticAmount'>
                                        <el-radio-group v-model="updateData.staticAmount" @change="change2">
                                            <el-radio size="small" label='1' value='1'>统计</el-radio>
                                            <el-radio size="small" label='2' value='2'>不统计</el-radio>
                                        </el-radio-group>
                                    </el-form-item>
                                </el-col>
                            </el-row>

                            <div v-if="show">
                                <el-row>
                                    <el-col :span="12">    
                                        <el-form-item label="统计方式" prop='staticWay'>
                                            <el-select size="small"  v-model="updateData.staticWay">
                                                <el-option :value="'1'" :label="'按记录条数'"></el-option>
                                                <el-option :value="'2'" :label="'按费用金额'"></el-option>
                                            </el-select>
                                        </el-form-item>
                                    </el-col>    
                                    
                                    <el-col :span="12">
                                        <el-form-item label="费用金额字段" prop='feeColumn'>
                                            <el-select size="small" v-model="updateData.feeColumn">
                                                <el-option :value="'1'" :label="'费用金额'"></el-option>
                                            </el-select>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                                <el-row>
                                    <el-col :span="12">     
                                        <el-form-item label="分组字段" prop='groupColumn'>
                                            <el-select size="small" v-model="updateData.groupColumn">
                                                <el-option :value="'1'" :label="'费用类别'"></el-option>
                                            </el-select>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                            </div>

                            <div v-if="false">
                                <el-form-item label="业务表名" prop='tableName'>
                                    <input size="small" v-model="updateData.tableName"></input>
                                </el-form-item>
                                <el-form-item label="业务表描述" prop='tableDesc'>
                                    <input size="small" v-model="updateData.tableDesc"></input>
                                </el-form-item>
                            </div>
                        </el-form>
                    </el-scrollbar>
                </div>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="closeVisible2">取 消</el-button>
                    <el-button type="primary" @click="update(updateData)">确 定</el-button>
                </span>
            </el-dialog>
        </el-container>
    </div>
</template>

<script>
    import Vue from 'vue';
    import { Message } from 'element-ui';
    Vue.component(Message.name, Message);
export default {
    data(){
       return{
            formdata:{
                type:'',
                catagory:''
            },
            updateData:{
                staticWay:'',
                feeColumn:'',
                groupColumn:'',
            },
            addData:{},
            tabledata:[],
            radio:'',
            currentPage: 1,
            pagesize: 5,
            updateVisible:false,
            addVisible:false,
            business:[],
            show: false,
            btnEnable:true
       }
    },
    methods:{
        getPageData(){
            let that=this;
            return that.tabledata.slice((that.currentPage-1)*that.pagesize,that.currentPage*that.pagesize);
            
        },
        getAllData(){
            let that=this;
            const loading = this.$loading({
                lock: true,
                text: 'Loading',
                spinner: 'el-icon-loading',
            });
            this.$http.post("/businessInfo/selectList",{}).then(function(result){
                that.tabledata = result.data
                loading.close();
            })

        },
        //查询
        serch(formdata){
            let vm = this;
            this.$http.post("/businessInfo/selectList",formdata).then(function(result){
                vm.tabledata = result.data
            })
            this.radio = ''

        },
        
        //是否启用控制
        editValidFlag(row){
            console.log(row.validFlag)
            let that = this
            if(row.validFlag==0){
                this.$confirm('确定禁用    <span class="main-Clr-ft">'+ row.catagory   +'</span>    业务吗？','温馨提示',{
                    dangerouslyUseHTMLString: true,
                    confirmButtonText:'确定',
                    cancelButtonText:'取消',
                    type:'warning'
                }).then(() => {
                    const loading = this.$loading({
                        lock: true,
                        text: 'Loading',
                        spinner: 'el-icon-loading',
                    });
                    this.$http.post("/businessInfo/updateValidFlag",{'id':row.id,'validFlag':0}).then(function(result){
                        if(result.data=="1"){
                            Message.success('禁用成功');
                            that.getAllData();
                        }else{
                            Message.error('禁用失败');
                        }
                        loading.close();
                    })
                }).catch(() => {
                    Message.info('已取消禁用');
                    that.getAllData();
                })
            }else if(row.validFlag==1){
                const loading = this.$loading({
                    lock: true,
                    text: 'Loading',
                    spinner: 'el-icon-loading',
                });
                   this.$http.post("/businessInfo/updateValidFlag",{'id':row.id,'validFlag':1}).then(function(result){
                       if(result.data=="1"){
                           Message.success('启用成功')
                           that.getAllData();
                       }else{
                           Message.error('启用失败');
                       }
                       loading.close();
                   })
            }
        },

        //选择项发生变化时触发
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },

        //选择每页显示条数时触发
        handleSizeChange(val) {
            this.pagesize=val;
        },

        //设置表头颜色
        getRowClass({ rowIndex }) {
            if (rowIndex == 0) {
                return 'background:#F5F7FA'
            } else {
                return ''
            }
        },
        
        //添加弹窗
        addValidFlag(){
            this.addVisible = true
        },
        //添加窗口下拉列表回填
        selectId(id){
            let that = this
            this.$http.post("/business/selectId",{catagoryCode:id}).then(function(result){
                that.addData = result.data 
            })
        },

        //重置
            resetMessage (formName) {
                this.$refs[formName].resetFields();
                this.getAllData();
            },

        //录入
        add(addData){
            console.log(addData)
            let that = this
            this.$http.post("/businessInfo/insert",addData).then(function(result){
                if(result.data=='1'){
                    Message.success('录入成功')
                    that.addVisible = false
                    that.$refs['addData'].resetFields();
                    that.getAllData();
                    that.show = false
                }else{
                    Message.error('录入失败');
                    that.addVisible = false
                    that.$refs['addData'].resetFields();
                    that.getAllData();
                    that.show = false
                }
            })
            
        },
        //添加窗口是否统计
        change1(){
            if(this.addData.staticAmount=='1'){
                this.show = true
            }else if(this.addData.staticAmount=='2'){
                this.show = false
            }
        },
        //添加弹窗×按钮
        dialogClose1(){
            this.addVisible = false
            this.$refs['addData'].resetFields();
            this.getAllData();
            this.show = false
        },
        //添加弹窗取消按钮
        closeVisible1(){
            this.addVisible = false
            this.$refs['addData'].resetFields();
            this.getAllData();
            this.show = false
        },

        //修改按钮是否可用
        updateEnable(){
            if(this.radio!=null){
                this.btnEnable = false
            }
        },

        //修改弹窗
        updateValidFlag(id){
            this.updateVisible = true
            let that = this
            this.$http.post('/businessInfo/selectId',{id:id}).then(function(result){
                that.updateData = result.data
                if(that.updateData.staticAmount=='1'){
                    that.show = true
                }else if(that.updateData.staticAmount=='2'){
                    that.show = false
                }
            })
            
        },

        //修改
        update(updateData){
            console.log(updateData)
            let that = this
            this.$http.post("/businessInfo/update",updateData).then(function(result){
                if(result.data=='1'){
                    Message.success('修改成功')
                    that.updateVisible = false
                    that.$refs['updateData'].resetFields();
                    that.getAllData();
                    that.show = false
                }else{
                    Message.error('修改失败');
                    that.updateVisible = false
                    that.$refs['updateData'].resetFields();
                    that.getAllData();
                    that.show = false
                }
            })
            
        },
        //修改是否统计
        change2(){
            if(this.updateData.staticAmount=='1'){
                this.show = true
            }else if(this.updateData.staticAmount=='2'){
                this.show = false
                this.updateData.staticWay = ''
                this.updateData.feeColumn = ''
                this.updateData.groupColumn = ''
            }
            console.log(this.updateData.staticWay)
        },
        //添加弹窗×按钮
        
        dialogClose2(){
            this.updateVisible = false
            this.$refs['updateData'].resetFields();
            this.getAllData();
            this.show = false
        },
        //添加弹窗取消按钮
        closeVisible2(){
            this.updateVisible = false
            this.$refs['updateData'].resetFields();
            this.getAllData();
            this.show = false
        },
        
    },

    mounted() {
        let that=this;
        that.getAllData();
        that.$http.post("/business/selectList").then(function(result){
            that.business = result.data
            
        })
    }
}
</script>

<style scoped>
    .headerCoster{
        height: 59px;
        border-top: 1px solid #eee;
        border-bottom: 1px solid #eee;
    }

    .deleteButtonColor{
        color: red;
    }

    .letBorder{
        border-left: #DCDFE6 solid 1px;
    }

    .cardWidth{
        width: 100%;
    }
    /deep/ .el-scrollbar__wrap {
        overflow-x: hidden;
    }
    /deep/ .elDiv{
        position: relative;
        top: 20px;
        margin: 0 auto;
    }
</style>