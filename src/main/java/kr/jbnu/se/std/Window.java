package kr.jbnu.se.std;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Creates frame and set its properties.
 *
 * @author www.gametutorial.net
 */
public class Window extends JFrame {

    // 창이 전체 화면인지 여부를 나타내는 플래그
    private boolean isFullScreen = false;

    // 원래 프레임 크기
    private final int originalFrameWidth = 800;
    private final int originalFrameHeight = 600;

    private Window() {
        // Sets the title for this frame.
        this.setTitle("Shoot the duck");

        // Size of the frame.
        this.setSize(originalFrameWidth, originalFrameHeight);

        // Center the window on the screen.
        this.setLocationRelativeTo(null);

        // So that frame can be resizable by the user.
        this.setResizable(true);

        // Exit the application when the user closes the frame.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add the game content (Framework).
        this.setContentPane(new Framework());

        // Register listener for window resize events.
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // 프레임 크기가 변경될 때 호출
                Dimension newSize = getSize();
                Framework.frameWidth = newSize.width;
                Framework.frameHeight = newSize.height;
            }
        });

        // Set the window to be visible.
        this.setVisible(true);
    }

    /**
     * 창을 전체 화면으로 전환하거나 창 모드로 되돌림
     */
    private void toggleFullScreen() {
        if (isFullScreen) {
            // 창 모드로 돌아가기
            this.dispose();
            this.setUndecorated(false);  // 전체화면일 때는 장식이 없어야 하지만, 창 모드로 돌아갈 때 장식을 추가
            this.setSize(originalFrameWidth, originalFrameHeight);  // 원래 크기로 복구
            this.setLocationRelativeTo(null);  // 다시 중앙으로 위치시킴
            this.setVisible(true);
            isFullScreen = false;
        } else {
            // 전체 화면 모드로 전환
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            this.dispose();
            this.setUndecorated(true);  // 전체화면 모드로 전환할 때 장식을 제거
            this.setSize(screenSize);   // 화면의 전체 크기로 변경
            this.setLocation(0, 0);     // 창을 화면 맨 위에 맞춤
            this.setVisible(true);
            isFullScreen = true;
        }
    }

    public static void main(String[] args) {
        // Use the event dispatch thread to build the UI for thread-safety.
        SwingUtilities.invokeLater(() -> {
            Window window = new Window();

            // 전체 화면 토글 기능 테스트용
            // "F" 키를 눌렀을 때 전체 화면 전환
            window.addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyPressed(java.awt.event.KeyEvent e) {
                    if (e.getKeyCode() == java.awt.event.KeyEvent.VK_F) {
                        window.toggleFullScreen();
                    }
                }
            });
        });
    }
}
