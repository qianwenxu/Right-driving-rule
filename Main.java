import java.util.Random;


public class Main {
	 public static void main(String[] args){
		 int carnum=0;
		 int outcar=0;//行驶过的车号
		 int highway_length=8000;//8km
		 int safety_dist=100;//100m
		 int max_car_num=400;
		 int overtaking_dist=800;
		 car eachcar[]=new car[max_car_num];
		 int left_v=33;//左侧车道速度
		 int overtake_num=0;//记录超车辆数
		 double is_busy=0.9;
		 int danger_dist=40;//危险距离40m
		 for(int i=0;i<1000;i++){//时间
			 Random r = new Random();
			 double u = r.nextDouble();
			 if(u<is_busy&&(carnum==0||eachcar[carnum-1].dist>safety_dist)){
				 eachcar[carnum]=new car();
				 eachcar[carnum].dist=0;
				 eachcar[carnum].lane=1;
				 eachcar[carnum].v=(int)Math.floor(r.nextDouble()*16)+17;
				 eachcar[carnum].time=i;
				 //System.out.println(carnum+"速度:"+eachcar[carnum].v);
				 carnum+=1;
			 }
			 for(int j=outcar;j<carnum;j++){//排队的车
				 if(eachcar[j].lane==1){
					 eachcar[j].dist+=eachcar[j].v;//右侧车道 
				 }else{
					 eachcar[j].dist+=left_v;//左侧
				 }
				 if(eachcar[j].dist>highway_length){
					 outcar++;
					 eachcar[j].dist=-1;
					 eachcar[j].time=i-eachcar[j].time;
				 }
				 if(j!=0){
					 if(eachcar[j-1].dist-eachcar[j].dist>0&&eachcar[j-1].dist-eachcar[j].dist<=safety_dist&&eachcar[j-1].lane==1&&eachcar[j].lane==1){//j超j-1
						 if(eachcar[j-1].dist-eachcar[j].dist<danger_dist){
							 System.out.println("危险！");
						 }
						 if(j-2>=outcar){
							 if(eachcar[j-1].v+6<=eachcar[j].v&&eachcar[j-2].dist-eachcar[j].dist>overtaking_dist){
								 eachcar[j].lane=2;
								 overtake_num++;
							 }else{
								 eachcar[j].dist=eachcar[j-1].dist-safety_dist;
								 eachcar[j].v=eachcar[j-1].v;
							 }
						 }else{
							 eachcar[j].dist=eachcar[j-1].dist-safety_dist;
							 eachcar[j].v=eachcar[j-1].v;
						 }
					 }else if(eachcar[j].dist-eachcar[j-1].dist>safety_dist&&eachcar[j].lane==2&&eachcar[j-1].lane==1){//换回车道
						 //int tempdist=eachcar[j-1].dist;
						 int tempv=eachcar[j-1].v;
						 eachcar[j-1].dist+=safety_dist;
						 eachcar[j-1].v=eachcar[j].v;
						 eachcar[j].lane=1;
						 eachcar[j].dist=eachcar[j-1].dist-safety_dist;
						 eachcar[j].v=tempv;
					 }
				 }
			 }
		 }
		 double time_ave=0;
		 for(int i=0;i<outcar;i++){
			 time_ave+=eachcar[i].time;
		 }
		 time_ave=time_ave/outcar;
		 System.out.println("共有车辆数："+carnum);
		 System.out.println("超车辆数："+overtake_num);
		 System.out.println("行驶到尽头车辆数："+outcar);
		 System.out.println("经过这段高速的平均时间："+time_ave); 
	 }
}
