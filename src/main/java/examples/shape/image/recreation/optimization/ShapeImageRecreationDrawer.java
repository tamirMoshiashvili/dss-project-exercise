package examples.shape.image.recreation.optimization;

import image.recreation.ImageRecreation;
import image.recreation.shape.ShapeSpecification;
import optimization.evaluation.OptimizationStepEvaluation;
import optimization.step.listener.OptimizationStepListener;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ShapeImageRecreationDrawer implements OptimizationStepListener<ImageRecreation<ShapeSpecification>> {
	private final BufferedImage targetImage;
	private final JLabel generationDataLabel;
	private final ImageIcon imageIcon;
	private final JPanel predictedImagePanel;

	public ShapeImageRecreationDrawer(BufferedImage targetImage) {
		this.targetImage = targetImage;
		this.generationDataLabel = new JLabel();
		this.imageIcon = new ImageIcon();
		this.predictedImagePanel = createPredictedImagePanel();

		initializeWindow();
	}

	private JPanel createPredictedImagePanel() {
		JPanel panel = new JPanel();
		panel.add(new JLabel(imageIcon));
		return panel;
	}

	private void initializeWindow() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(50 + 2 * targetImage.getWidth(), 80 + targetImage.getHeight()));
		frame.add(createMainPanel());
		frame.setVisible(true);
	}

	private JPanel createMainPanel() {
		JPanel mainPanel = new JPanel();
		BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(layout);

		mainPanel.add(createGenerationNumberPanel());
		mainPanel.add(createImagesPanel());
		return mainPanel;
	}

	private JPanel createGenerationNumberPanel() {
		JPanel generationPanel = new JPanel();
		generationPanel.setLayout(new GridBagLayout());

		generationPanel.add(generationDataLabel);
		return generationPanel;
	}

	private JPanel createImagesPanel() {
		JPanel mainPanel = new JPanel();
		BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.X_AXIS);
		mainPanel.setLayout(layout);

		mainPanel.add(createTargetImagePanel());
		mainPanel.add(predictedImagePanel);
		return mainPanel;
	}

	private JPanel createTargetImagePanel() {
		JPanel targetImagePanel = new JPanel();
		targetImagePanel.add(new JLabel(new ImageIcon(targetImage)));
		return targetImagePanel;
	}

	@Override
	public void notifyOptimizationStepStarted() {

	}

	@Override
	public void notifyOptimizationStepEnded(OptimizationStepEvaluation<ImageRecreation<ShapeSpecification>> stepEvaluation) {
		ImageRecreation<ShapeSpecification> imageRecreation = stepEvaluation.getBestSolution();
		BufferedImage recreatedImage = imageRecreation.recreateImage(targetImage.getWidth(), targetImage.getHeight());

		imageIcon.setImage(recreatedImage);
		generationDataLabel.setText(String.format("Generation: %d | Num Shapes: %d",
				stepEvaluation.getStep(), imageRecreation.getItems().size()));

		repaint(predictedImagePanel);
		repaint(generationDataLabel);
	}

	private void repaint(JComponent component) {
		component.revalidate();
		component.repaint();
	}
}
