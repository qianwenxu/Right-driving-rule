import java.util.Random;


public class Main1 {
	 public static void main(String[] args){
		 int carnum=0;
		 int outcar=0;//行驶过的车号
		 int highway_length=8000;//8km
		 int safety_dist=100;//100m
		 int max_car_num=100;
		 int overtaking_dist=800;
		 car righteachcar[]=new car[max_car_num];
		 car lefteachcar[]=new car[max_car_num];
		 int left_carnum=0;
		 for(int i=0;i<1000;i++){
			 Random r = new Random();
			 double u = r.nextDouble();
			 if(u>0.5&&(carnum==0||righteachcar[carnum-1].dist>safety_dist)){
				 righteachcar[carnum]=new car();
				 righteachcar[carnum].dist=0;
				 righteachcar[carnum].lane=1;
				 righteachcar[carnum].v=(int)Math.floor(r.nextDouble()*16)+17;
				 carnum+=1;
			 }
			 for(int j=0;j<carnum;j++){//右侧车道
				 righteachcar[j].dist+=righteachcar[j].v;
				 if(righteachcar[j].dist>highway_length){
					 outcar++;
					 righteachcar[j].dist=-1;
				 }
				 if(j!=0){
					 if(righteachcar[j-1].dist-righteachcar[j].dist<=safety_dist){//j超j-1
						 if(j-2>=outcar){
							 if(righteachcar[j-1].v+6<righteachcar[j].v){
								 if(righteachcar[j-2].dist-righteachcar[j].dist>overtaking_dist){
									 int k=0;
									 int flag=-1;
									 for(;k<left_carnum;k++){
										 if(lefteachcar[k].dist<righteachcar[j].dist){
											flag=k;break; 
										 }
									 }
									 if(flag==-1){
										 if(left_carnum<1||((lefteachcar[left_carnum-1].dist-righteachcar[j].dist)>overtaking_dist)){
											 left_carnum+=1;
											 carnum-=1;
											 lefteachcar[left_carnum-1].dist=righteachcar[j].dist;
											 lefteachcar[left_carnum-1].lane=2;
											 lefteachcar[left_carnum-1].v=righteachcar[j].v;
											 for(int s=j;s<carnum;s++){
												 righteachcar[s].dist=righteachcar[s+1].dist;
												 righteachcar[s].v=righteachcar[s+1].v;
											 }
										 }else{
											 righteachcar[j].dist=righteachcar[j-1].dist-safety_dist;
											 righteachcar[j].v=righteachcar[j-1].v;
										 }
									 }else{
										 if(flag==0||((lefteachcar[flag-1].dist-righteachcar[j].dist)>overtaking_dist)){
											 left_carnum+=1;
											 carnum-=1;
											 for(int s=flag+1;s<left_carnum;s++){
												 lefteachcar[s].dist=lefteachcar[s-1].dist;
												 lefteachcar[s].v=lefteachcar[s-1].v;
											 }
											 lefteachcar[flag].dist=righteachcar[j].dist;
											 lefteachcar[flag].lane=2;
											 lefteachcar[flag].v=righteachcar[j].v;
											 for(int s=j;s<carnum;s++){
												 righteachcar[s].dist=righteachcar[s+1].dist;
												 righteachcar[s].v=righteachcar[s+1].v;
											 }
										 }else{
											 righteachcar[j].dist=righteachcar[j-1].dist-safety_dist;
											 righteachcar[j].v=righteachcar[j-1].v;
										 }
									 }
								 }else{
									 righteachcar[j].dist=righteachcar[j-1].dist-safety_dist;
									 righteachcar[j].v=righteachcar[j-1].v;
								 }  
							 }
						 }
					 } 
				 }
			 }
		 }
	 }
}
