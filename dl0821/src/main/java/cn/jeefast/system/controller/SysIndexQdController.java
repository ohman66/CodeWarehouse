package cn.jeefast.system.controller;


import cn.jeefast.common.base.BaseController;
import cn.jeefast.common.utils.R;
import cn.jeefast.system.entity.*;
import cn.jeefast.system.service.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author theodo
 * @since 2021-11-26
 */
@RestController
@RequestMapping("/sysIndexQd")
public class SysIndexQdController extends BaseController {

    @Value("${server.port}")
    private String serverport;

    @Value("${server.ipaddressInfo}")
    private String ipaddressInfo;

    @Value("${server.context-path}")
    private String servercontextpath;


    @Autowired
    private SysUserTokenService sysUserTokenService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysReserveService sysReserveService;

    @Autowired
    private SysDingdanService sysDingdanService;

    @Autowired
    private SysFoodService sysFoodService;

    @Autowired
    private TMaterialFileService tMaterialFileService;

    @Autowired
    private SysGwcService sysGwcService;

    @Autowired
    private SysCzjlbService sysCzjlbService;

    @Autowired
    private SysYhqbglService sysYhqbglService;




    /**
     * 获取用户信息
     */
    @RequestMapping("/getUser")
    public R getUser(@RequestBody Map<String,Object> tjarray){
        System.out.println("tjarraytjarray"+tjarray);

        SysUserToken sysUserToken = sysUserTokenService.selectOne(new EntityWrapper<SysUserToken>().eq("token",tjarray.get("token")+""));
        SysUser user = sysUserService.selectById(sysUserToken.getUserId());
        System.out.println("useruseruseruser"+user);
        return R.ok().put("sysuser",user);

    }



    /**
     * 个人信息更新
     */
    @RequestMapping("/updateuser")
    public R updateuser(@RequestBody SysUser sysUser){
        System.out.println("sysUsersysUser"+sysUser);
        sysUserService.updateById(sysUser);
        return R.ok();

    }

    /**
     * 获取餐位信息
     */
    @RequestMapping("/getReserve")
    public R getReserve(@RequestBody Map<String,Object> tjarray){
        System.out.println("tjarraytjarray"+tjarray);
        List<SysReserve> reserveList = sysReserveService.selectList(new EntityWrapper<SysReserve>());
        if(reserveList.size()>0){
          for(int i=0;i<reserveList.size();i++){
              SysReserve sysReserve = reserveList.get(i);

          }
        }
        System.out.println("reserveListreserveListreserveList"+reserveList);
        return R.ok().put("reserveList",reserveList);

    }

    /**
     * 预定餐位信息
     */
    @RequestMapping("/ydcw")
    public R ydcw(@RequestBody Map<String,Object> temp){
        System.out.println("temptemptemptemptemp"+temp);
        String token = temp.get("token")+"";
        SysUserToken sysUserToken = sysUserTokenService.selectOne(new EntityWrapper<SysUserToken>().eq("token",token));
        SysUser user = sysUserService.selectById(sysUserToken.getUserId());


        SysReserve sysReserve = sysReserveService.selectById(temp.get("itemid")+"");
        List<SysDingdan> sysDingdanoldList = sysDingdanService.selectList(new EntityWrapper<SysDingdan>().eq("parintid",sysReserve.getId()).eq("ddtype","2"));
        if(sysDingdanoldList.size()>0){
            return R.error("该餐位已被预订，请联系管理员");
        }

        SysDingdan sysDingdan = new SysDingdan();
        sysDingdan.setUpdatetime(new Date());
        sysDingdan.setDdtype("2");
        sysDingdan.setParintid(sysReserve.getId());
        sysDingdan.setDdje(sysReserve.getYudfy()+"");
        sysDingdan.setYdry(user.getUsername());
        sysDingdanService.insert(sysDingdan);

        return R.ok();

    }

    /**
     * 获取物品信息列表
     */
    @RequestMapping("/getFoodListAll")
    public R getFoodListAll(@RequestBody Map<String,Object> tjarray) throws UnknownHostException {
        System.out.println("tjarraytjarraytjarray"+tjarray);
//        Wrapper w =new EntityWrapper<SysFood>();
//        if(tjarray.get("foodname") != null && !((tjarray.get("foodname")+"").trim()).equals("")){
//            w.like("foodname",tjarray.get("foodname")+"");
//        }
        Map<String,Object> queryMap =new HashMap<>();
        if(tjarray.get("foodname") != null && !((tjarray.get("foodname")+"").trim()).equals("")){
            queryMap.put("foodname",tjarray.get("foodname")+"");
        }
        queryMap.put("issj","1");
        List<SysFood> foodList = sysFoodService.selectListByMap(queryMap);

        if(foodList.size()>0){
            for(int i=0;i<foodList.size();i++){
                SysFood sysFood = foodList.get(i);
                //获取附件列表
                sysFood = getFjSysFood(sysFood);
            }

        }
        System.out.println("foodListfoodListfoodList"+foodList);
        return R.ok().put("foodList",foodList);

    }

    public SysFood getFjSysFood(SysFood sysFood) throws UnknownHostException {
        List<TMaterialFile> tMaterialFiles = tMaterialFileService.selectList(new EntityWrapper<TMaterialFile>().eq("parentid",sysFood.getId()));
        List<Map<String,Object>> mapList = new ArrayList<>();
        if(!tMaterialFiles.isEmpty()){
            for(TMaterialFile tMaterialFile:tMaterialFiles){
                Map<String,Object> map =new HashMap<>();
                map.put("id",tMaterialFile.getId());
                map.put("filePath",tMaterialFile.getSfilename());
                map.put("fileName",tMaterialFile.getSaccessoryname());
                mapList.add(map);
            }
        }
        JSONArray json = (JSONArray) JSONArray.toJSON(mapList);
        sysFood.setFiles(json);
        if(sysFood.getLocalcolor() != null){//发源地分类
            String value = sysFood.getLocalcolor();
            if(value.equals("1")){
                sysFood.setLocalcolorname("日常用品");
            }else if(value.equals("2")){
                sysFood.setLocalcolorname("厨卫用品");
            }else if(value.equals("3")){
                sysFood.setLocalcolorname("家电用品");
            }else if(value.equals("4")){
                sysFood.setLocalcolorname("家具以及家居装饰用品");
            }else{
                sysFood.setLocalcolorname("其他类");
            }
        }

//        InetAddress address = InetAddress.getLocalHost();
//        System.out.println("addressaddressaddress"+address);
        sysFood.setTp("http://"+ipaddressInfo +":"+serverport+"/"+servercontextpath+"/upload/"+tMaterialFiles.get(0).getSfilename());
        return sysFood;
    }

    /**
     * 获取物品信息
     */
    @RequestMapping("/getFood")
    public R getFood(@RequestBody Map<String,Object> tjarray) throws UnknownHostException {
        System.out.println("tjarraytjarraytjarray"+tjarray);
        if(tjarray.get("id") == null){
            return R.error("请选择要查看的物品");
        }
        String id = tjarray.get("id")+"";
        SysFood sysFood =sysFoodService.selectById(id);
        sysFood = getFjSysFood(sysFood);
        System.out.println("sysFoodsysFoodsysFoodsysFood"+sysFood);
        return R.ok().put("food",sysFood);

    }

    /**
     * 获取物品信息
     */
    @RequestMapping("/getFoodsById")
    public R getFoodsById(@RequestBody Map<String,Object> tjarray) throws UnknownHostException {
        System.out.println("tjarraytjarraytjarray"+tjarray);
        if(tjarray.get("id") == null){
            return R.error("请选择要查看的物品");
        }
        String id = tjarray.get("id")+"";
        List<String> ids = new ArrayList<>();
        if(id.indexOf(",") >= 0){
            System.out.println("字符串中有逗号");
            ids = Arrays.asList(id.split(","));
        }else{
            ids.add(id) ;
        }
        System.out.println("idsidsidsidsids"+ids);
        List<SysFood> foodlist = new ArrayList<>();
        Double zongjiage = 0.00;
        if(ids.size()>0){
            for(String gwcid:ids){
                SysGwc sysGwc = sysGwcService.selectById(gwcid);
                String foodid = null;
                if(sysGwc != null){
                    foodid = sysGwc.getFoodid();
                }else{
                    foodid = gwcid;
                }
                SysFood sysFood =sysFoodService.selectById(foodid);
                if(sysFood == null){
                    return R.error("存在已删除的物品，无法进行购买！");
                }
                System.out.println("kkkkkkkkkkkkkkkkkkk"+sysFood);
                sysFood = getFjSysFood(sysFood);
                System.out.println("sysFoodsysFoodsysFoodsysFood"+sysFood);
                zongjiage = zongjiage+sysFood.getJiage();
                foodlist.add(sysFood);
            }
        }

        return R.ok().put("foodlist",foodlist).put("zongjiage",zongjiage);

    }

    /**
     * 预定物品信息
     */
    @RequestMapping("/ydcp")
    public R ydcp(@RequestBody JSONObject temp){
        String token = temp.get("token")+"";
        SysUserToken sysUserToken = sysUserTokenService.selectOne(new EntityWrapper<SysUserToken>().eq("token",token));
        SysUser user = sysUserService.selectById(sysUserToken.getUserId());
        System.out.println("slsslsslsslsslssls"+temp.getString("sl"));
        String slParam = temp.getString("sl");
        String slParamStart = slParam.substring(0, slParam.indexOf(","));
        slParam = slParam.substring(slParamStart.length()+1, slParam.length());
        System.out.println("slParamslParamslParam"+slParam);

        List<String> slArray = Arrays.asList(slParam.split(","));
        System.out.println("ttttttttttt"+ JSON.toJSONString(slArray));
//        slArray.remove("");
//        System.out.println("slArrayslArrayslArray"+ JSON.toJSONString(slArray));

        System.out.println("temptemptemptemptemptemp"+temp);
        System.out.println("foodlistfoodlistfoodlist"+temp.get("foodlist"));


        JSONArray jsonArray = temp.getJSONArray("foodlist");

        //计算用户的余额是否能够充足支付
        Double pdje = 0.00;
        if(jsonArray.size()>0) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                System.out.println("idididididididvvssssssssss"+jsonObject.get("id"));
                SysFood sysFood = sysFoodService.selectById(jsonObject.get("id")+"");
                pdje = (sysFood.getJiage()+sysFood.getBzj())+pdje;
            }
        }
        SysYhqbgl sysYhqbgl = sysYhqbglService.selectOne(new EntityWrapper<SysYhqbgl>().eq("username",user.getUsername()));
        if(sysYhqbgl != null){
            if(pdje > sysYhqbgl.getYue()){
                return R.error("余额不足，无法租赁下单，请及时进行充值！");
            }
        }else{
            return R.error("你还未进行充值，无法租赁下单，请及时进行充值！");
        }

        String uuid = UUID.randomUUID().toString();    //转化为String对象
        uuid = uuid.replace("-", "");//合计下单号
        if(jsonArray.size()>0){
            for(int i=0;i<jsonArray.size();i++){
                System.out.println("getgetgetgetget"+jsonArray.get(i));
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                System.out.println("idididididididvvssssssssss"+jsonObject.get("id"));
                SysFood sysFood = sysFoodService.selectById(jsonObject.get("id")+"");
                SysDingdan sysDingdan = new SysDingdan();
                sysDingdan.setUpdatetime(new Date());
                sysDingdan.setDdtype("1");
                sysDingdan.setParintid(sysFood.getId());
                sysDingdan.setDdje((sysFood.getJiage()+sysFood.getBzj())+"");
                sysDingdan.setYdry(user.getUsername());
                sysDingdan.setKssj(new Date());//下单时间
                sysDingdan.setShdz(temp.get("address")+"");
                sysDingdan.setSl(Integer.parseInt(slArray.get(i)));
                sysDingdan.setZongjiage(Double.valueOf(temp.get("zongjiage")+""));
                sysDingdan.setHjxdh(uuid);
                sysDingdanService.insert(sysDingdan);
                sysYhqbgl.setYue(sysYhqbgl.getYue()-(sysFood.getJiage()+sysFood.getBzj()));
                sysYhqbglService.updateById(sysYhqbgl);



            }
        }


        return R.ok();

    }

    /**
     * 预定物品信息
     */
    @RequestMapping("/selectLocalcolor")
    public R selectLocalcolor(@RequestBody Map<String,Object> temp) throws UnknownHostException {
        System.out.println("temptemptemptemptemp"+temp);
        Map<String,Object> queryMap = new HashMap<>();
        if(temp.get("itemid") != null && !((temp.get("itemid")+"").trim()).equals("")){
            queryMap.put("localcolor",temp.get("itemid")+"");
        }
        List<SysFood> foodList = sysFoodService.selectListByMap(queryMap);
//        List<SysFood> foodList = sysFoodService.selectList(new EntityWrapper<SysFood>().eq("localcolor",temp.get("itemid")+"").orderBy("updatetime"));
        if(foodList.size()>0){
            for(int i=0;i<foodList.size();i++){
                SysFood sysFood = foodList.get(i);
                sysFood = getFjSysFood(sysFood);
            }

        }
        return R.ok().put("foodList",foodList);

    }

    /**
     * 预定物品信息
     */
    @RequestMapping("/selectfoodtype")
    public R selectfoodtype(@RequestBody Map<String,Object> temp) throws UnknownHostException {
        System.out.println("temptemptemptemptemp"+temp);
        Map<String,Object> queryMap = new HashMap<>();
        if(temp.get("itemid") != null && !((temp.get("itemid")+"").trim()).equals("")){
            queryMap.put("foodtype",temp.get("itemid")+"");
        }
        List<SysFood> foodList = sysFoodService.selectListByMap(queryMap);
//        List<SysFood> foodList = sysFoodService.selectList(new EntityWrapper<SysFood>().eq("foodtype",temp.get("itemid")+"").orderBy("updatetime"));
        if(foodList.size()>0){
            for(int i=0;i<foodList.size();i++){
                SysFood sysFood = foodList.get(i);
                sysFood = getFjSysFood(sysFood);
            }

        }
        return R.ok().put("foodList",foodList);

    }

    /**
     * 预定物品信息
     */
    @RequestMapping("/selectisrxsp")
    public R selectisrxsp(@RequestBody Map<String,Object> temp) throws UnknownHostException {
        System.out.println("temptemptemptemptemp"+temp);
        Map<String,Object> queryMap =new HashMap<>();
        if(temp.get("itemid") != null && !((temp.get("itemid")+"").trim()).equals("")){
            queryMap.put("isrxsp",temp.get("itemid")+"");
        }
        List<SysFood> foodList = sysFoodService.selectListByMap(queryMap);
//        List<SysFood> foodList = sysFoodService.selectList(new EntityWrapper<SysFood>().eq("isrxsp",temp.get("itemid")+"").orderBy("updatetime"));
        if(foodList.size()>0){
            for(int i=0;i<foodList.size();i++){
                SysFood sysFood = foodList.get(i);
                sysFood = getFjSysFood(sysFood);
            }

        }
        return R.ok().put("foodList",foodList);

    }
    /**
     * 预定物品信息
     */
    @RequestMapping("/selecttaste")
    public R selecttaste(@RequestBody Map<String,Object> temp) throws UnknownHostException {
        System.out.println("temptemptemptemptemp"+temp);
        List<SysFood> foodList = sysFoodService.selectList(new EntityWrapper<SysFood>().eq("taste",temp.get("itemid")+"").orderBy("updatetime"));
        if(foodList.size()>0){
            for(int i=0;i<foodList.size();i++){
                SysFood sysFood = foodList.get(i);
                sysFood = getFjSysFood(sysFood);
            }

        }
        return R.ok().put("foodList",foodList);

    }


    /**
     * 预定收藏产品信息
     */
    @RequestMapping("/ydgwc")
    public R ydgwc(@RequestBody Map<String,Object> temp){
        String token = temp.get("token")+"";
        SysUserToken sysUserToken = sysUserTokenService.selectOne(new EntityWrapper<SysUserToken>().eq("token",token));
        SysUser user = sysUserService.selectById(sysUserToken.getUserId());
        System.out.println("gwcidsgwcidsgwcidsgwcids"+temp.get("gwcids"));
        String[] gwcidsArray = (temp.get("gwcids")+"").replace("\"", "").split(",");
        String hjddh = new SimpleDateFormat("yyyyMMddHHmmssSSS") .format(new Date());
        Double hjje = 0.00;
        for(String gwcids:gwcidsArray){
            System.out.println("gwcidsgwcidsgwcidsgwcids"+gwcids);
            SysGwc sysGwc = sysGwcService.selectById(gwcids);
            System.out.println("sysGwcsysGwcsysGwc"+sysGwc);
            if(sysGwc != null){
                SysFood sysFood = sysFoodService.selectById(sysGwc.getFoodid());
                SysDingdan sysDingdan = new SysDingdan();
                sysDingdan.setUpdatetime(new Date());
                sysDingdan.setDdtype("1");
                sysDingdan.setParintid(sysFood.getId());
                sysDingdan.setDdje(sysFood.getJiage()+"");
                sysDingdan.setYdry(user.getUsername());
                sysDingdan.setKssj(new Date());//下单时间
                sysDingdan.setShdz(sysGwc.getShdz());//物品送达地址
                sysDingdan.setHjxdh(hjddh);
                hjje += Double.valueOf(sysDingdan.getDdje());
                sysDingdanService.insert(sysDingdan);
            }

        }

        return R.ok().put("hjje",hjje);

    }

    /**
     * 获取收藏列表
     */
    @RequestMapping("/deletegwc")
    public R deletegwc(@RequestBody Map<String,Object> tjarray){
        System.out.println("tjarraytjarraytjarraytjarray"+tjarray);
        String gwcids = tjarray.get("gwcids")+"";
        gwcids = gwcids.replace("\"", "");
        String[] gwcArray = gwcids.split(",");
        if(gwcArray.length>0){
            for(String gwcid:gwcArray){
                sysGwcService.deleteById(gwcid);
            }
        }
        return R.ok();
    }

    /**
     * 获取收藏列表
     */
    @RequestMapping("/getGwcListAll")
    public R getGwcListAll(@RequestBody Map<String,Object> tjarray) throws UnknownHostException {
        if(tjarray.get("token") == null){
            return R.error("还未登录账号！请先登录。");
        }
        String token = tjarray.get("token")+"";
        SysUserToken sysUserToken = sysUserTokenService.selectOne(new EntityWrapper<SysUserToken>().eq("token",token));
        SysUser user = sysUserService.selectById(sysUserToken.getUserId());

        List<SysGwc> sysGwcList = sysGwcService.selectList(new EntityWrapper<SysGwc>().eq("createuser",user.getUsername()).orderBy(true,"updatetime",false));
        if(sysGwcList.size()>0){
            for(int i=0;i<sysGwcList.size();i++){
                SysGwc sysGwc = sysGwcList.get(i);
                SysFood sysFood = sysFoodService.selectById(sysGwc.getFoodid());
                //获取附件列表
                sysFood = getFjSysFood(sysFood);
                sysGwc.setSysFood(sysFood);

            }
        }
        return R.ok().put("sysGwcList",sysGwcList);
    }

    /**
     * 收藏产品信息
     */
    @RequestMapping("/jrgwc")
    public R jrgwc(@RequestBody Map<String,Object> temp){
        System.out.println("temptemptemp"+temp);
        String token = temp.get("token")+"";
        SysUserToken sysUserToken = sysUserTokenService.selectOne(new EntityWrapper<SysUserToken>().eq("token",token));
        SysUser user = sysUserService.selectById(sysUserToken.getUserId());

        List<SysGwc> sysGwcs = sysGwcService.selectList(new EntityWrapper<SysGwc>().eq("foodid",temp.get("itemid")+"").eq("createuser",user.getUsername()).orderBy(true,"updatetime",false));
        if(sysGwcs.size()>0){
            return R.error("该物品已经加入收藏了，请勿重复加入！");
        }
        SysGwc sysGwc = new SysGwc();
        sysGwc.setFoodid(temp.get("itemid")+"");
        sysGwc.setSl(Integer.parseInt(temp.get("sl")+""));//数量
        sysGwc.setCreateuser(user.getUsername());
        sysGwc.setCratetime(new Date());
        sysGwc.setUpdateuser(user.getUsername());
        sysGwc.setUpdatetime(new Date());
        System.out.println("sysGwcsysGwcsysGwcsysGwc"+sysGwc);
        sysGwcService.insert(sysGwc);

        return R.ok();

    }

    /**
     * 获取我的租赁列表
     */
    @RequestMapping("/getDingdanListAll")
    public R getDingdanListAll(@RequestBody Map<String,Object> tjarray) throws UnknownHostException {
        if(tjarray.get("token") == null){
            return R.error("还未登录账号！请先登录。");
        }
        String token = tjarray.get("token")+"";
        SysUserToken sysUserToken = sysUserTokenService.selectOne(new EntityWrapper<SysUserToken>().eq("token",token));
        SysUser user = sysUserService.selectById(sysUserToken.getUserId());
        List<SysDingdan> sysDingdanList = sysDingdanService.selectList(new EntityWrapper<SysDingdan>().eq("ydry",user.getUsername()).orderBy(true,"updatetime",false));
        if(sysDingdanList.size()>0){
            for(SysDingdan sysDingdan:sysDingdanList){
                SysFood sysFood = sysFoodService.selectById(sysDingdan.getParintid());
                //获取附件列表
                System.out.println("sysFoodsysFoodsysFoodaaaaaa"+sysFood);
                sysFood = getFjSysFood(sysFood);
                if(sysFood != null){
                    sysDingdan.setSysFood(sysFood);
                }
            }
        }

        return R.ok().put("sysDingdanList",sysDingdanList);
    }

    /**
     * 确认还租
     */
    @RequestMapping("/qrsh")
    public R qrsh(@RequestBody Map<String,Object> temp){
        String[] dingdanidsArray = (temp.get("dingdanids")+"").replace("\"", "").split(",");
        for(String dingdanid:dingdanidsArray){
            SysDingdan sysDingdan = sysDingdanService.selectById(dingdanid);
            sysDingdan.setSfqrsh("1");//确认还租
            sysDingdan.setBzjthsq("0");//保证金退还申请
            sysDingdanService.updateById(sysDingdan);
        }
        return R.ok();

    }


    /**
     * 在线充值
     */
    @RequestMapping("/saveJfgngl")
    public R saveJfgngl(@RequestBody JSONObject tjarray){
        System.out.println("tjarraytjarraytjarray"+ JSON.toJSONString(tjarray));
        Double jiage =  tjarray.getDouble("jiage");
        String token = tjarray.getString("token");
//        String type = tjarray.getString("type");
        String name = tjarray.getString("name");


        SysUserToken sysUserToken = sysUserTokenService.selectOne(new EntityWrapper<SysUserToken>().eq("token",token));
        SysUser user = sysUserService.selectById(sysUserToken.getUserId());
        SysCzjlb sysCzjlb = new SysCzjlb();
        sysCzjlb.setJine(jiage);
        sysCzjlb.setUsername(user.getUsername());
        sysCzjlb.setCreatetime(new Date());
        sysCzjlb.setCreateuser(user.getUsername());
        sysCzjlb.setUpdatetime(new Date());
        sysCzjlb.setUpdateuser(user.getUsername());
        sysCzjlbService.insert(sysCzjlb);
        //钱包修改
        List<SysYhqbgl> sysYhqbgls = sysYhqbglService.selectList(new EntityWrapper<SysYhqbgl>().eq("username",user.getUsername()));
        SysYhqbgl sysYhqbgl = new SysYhqbgl();
        if(sysYhqbgls.size() == 0){
            sysYhqbgl.setUsername(user.getUsername());
            sysYhqbgl.setCreatetime(new Date());
            sysYhqbgl.setCreateuser(user.getUsername());
            sysYhqbgl.setUpdatetime(new Date());
            sysYhqbgl.setUpdateuser(user.getUsername());
            sysYhqbgl.setYue(sysCzjlb.getJine());
        }else{
            sysYhqbgl =  sysYhqbgls.get(0);
            sysYhqbgl.setUpdatetime(new Date());
            sysYhqbgl.setUpdateuser(user.getUsername());
            sysYhqbgl.setYue(sysYhqbgl.getYue()+sysCzjlb.getJine());
        }

        sysYhqbglService.insertOrUpdate(sysYhqbgl);

        return R.ok();

    }

    /**
     * 充值记录
     */
    @RequestMapping("/getCzjlListAll")
    public R getCzjlListAll(@RequestBody JSONObject tjarray){
        String token = tjarray.getString("token");
        SysUserToken sysUserToken = sysUserTokenService.selectOne(new EntityWrapper<SysUserToken>().eq("token",token));
        SysUser user = sysUserService.selectById(sysUserToken.getUserId());
        List<SysCzjlb> czjlList = sysCzjlbService.selectList(new EntityWrapper<SysCzjlb>().eq("username",user.getUsername()).orderBy(true,"updatetime",false));
        return R.ok().put("czjlList",czjlList);
    }


}
