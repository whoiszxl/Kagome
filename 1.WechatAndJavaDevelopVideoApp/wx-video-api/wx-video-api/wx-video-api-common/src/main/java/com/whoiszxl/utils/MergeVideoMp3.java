package com.whoiszxl.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MergeVideoMp3 {

private String ffmpegEXE;
	
	public MergeVideoMp3(String ffmpegEXE) {
		super();
		this.ffmpegEXE = ffmpegEXE;
	}
	
	public void convertor(String videoInputPath, String mp3InputPath,
			double seconds, String videoOutputPath) throws Exception {
//		./ffmpeg.exe -i video.mp4 -i bgm.mp3 -map 0:0 -map 1:0 -t 3 -y output.mp4
		List<String> command = new ArrayList<>();
		command.add(ffmpegEXE);
		
		command.add("-i");
		command.add(videoInputPath);
		
		command.add("-i");
		command.add(mp3InputPath);
		
		command.add("-map");
		command.add("0:0");
		
		command.add("-map");
		command.add("1:0");
		
		command.add("-t");
		command.add(String.valueOf(seconds));
		
		command.add("-y");
		command.add(videoOutputPath);
		
		
		System.out.print("合并视频的命令：");
		for (String c : command) {
			System.out.print(c + " ");
		}
		System.out.println();
		
		ProcessBuilder builder = new ProcessBuilder(command);
		Process process = builder.start();
		
		InputStream errorStream = process.getErrorStream();
		InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
		BufferedReader br = new BufferedReader(inputStreamReader);
		
		String line = "";
		while ( (line = br.readLine()) != null ) {
		}
		
		if (br != null) {
			br.close();
		}
		if (inputStreamReader != null) {
			inputStreamReader.close();
		}
		if (errorStream != null) {
			errorStream.close();
		}
		
	}

	public static void main(String[] args) {
		MergeVideoMp3 ffmpeg = new MergeVideoMp3("E:\\ffmpeg\\bin\\ffmpeg.exe");
		try {
			ffmpeg.convertor("http://video.whoiszxl.com/video/11/c0e09ecf-5f1d-4e1b-8a60-1f7990cb73f3.mp4", "http://video.whoiszxl.com/bgm/yingting.mp3", 3, "C:\\java.mp4");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
